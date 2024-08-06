package com.ortega.admin.service;

import com.ortega.admin.models.DTO.request.ReservaRequest;
import com.ortega.admin.models.DTO.response.*;

import java.util.List;

public interface IReservaService {

    List<ReporteResponse> listarReservasParaReporte();
    List<ReservaListResponse> listarReservas();
    ReservaResponse obtenerReserva(int id);
    List<NacionalidadResponse> obtenerNacionalidades();
    PaqueteResponse obtenerPaquetes();
    String registrarReserva(ReservaRequest reservaRequest);
    String actualizarReserva(Integer idReserva, ReservaRequest reservaRequest);
}
