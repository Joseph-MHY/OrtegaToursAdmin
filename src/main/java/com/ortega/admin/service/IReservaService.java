package com.ortega.admin.service;

import com.ortega.admin.models.DTO.ReporteResponse;

import java.util.List;

public interface IReservaService {

    List<ReporteResponse> listarReservasParaReporte();
}
