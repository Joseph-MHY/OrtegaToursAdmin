package com.ortega.admin.service;

import com.ortega.admin.models.DTO.response.ReporteResponse;
import com.ortega.admin.models.DTO.response.ReservaResponse;

import java.util.List;

public interface IReservaService {

    List<ReporteResponse> listarReservasParaReporte();
    List<ReservaResponse> listarReservas();
}
