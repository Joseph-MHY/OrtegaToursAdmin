package com.ortega.admin.service;

import com.ortega.admin.models.DTO.response.NacionalidadResponse;
import com.ortega.admin.models.DTO.response.ReporteResponse;
import com.ortega.admin.models.DTO.response.ReservaListResponse;
import com.ortega.admin.models.DTO.response.ReservaResponse;
import com.ortega.admin.models.entity.Nacionalidades;

import java.util.List;

public interface IReservaService {

    List<ReporteResponse> listarReservasParaReporte();
    List<ReservaListResponse> listarReservas();
    ReservaResponse obtenerReserva(int id);
    List<NacionalidadResponse> obtenerNacionalidades();
}
