package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.ReporteResponse;
import com.ortega.admin.repositories.IReserva;
import com.ortega.admin.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReserva iReserva;

    @Override
    public List<ReporteResponse> listarReservasParaReporte() {
        List<Object[]> results = iReserva.findAllReservasForReport();
        List<ReporteResponse> reportes = new ArrayList<>();

        for (Object[] result : results) {
            ReporteResponse response = new ReporteResponse();
            response.setId_reserva((Integer) result[0]);
            response.setFecha_registro((Date) result[1]);
            response.setCliente((String) result[2]);
            response.setNumdocumento((String) result[3]);
            response.setNum_pasajeros((Integer) result[4]);
            response.setNombre_paquete((String) result[5]);
            response.setCategoria_paquete((String) result[6]);
            response.setTipo_viaje((String) result[7]);
            response.setConductor((String) result[8]);
            response.setCosto_total((BigDecimal) result[9]);
            reportes.add(response);
        }
        return reportes;
    }
}
