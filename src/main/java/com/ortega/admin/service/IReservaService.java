package com.ortega.admin.service;

import com.ortega.admin.models.DTO.response.*;
import com.ortega.admin.models.entity.Nacionalidades;

import java.util.List;

public interface IReservaService {

    List<ReporteResponse> listarReservasParaReporte();
    List<ReservaListResponse> listarReservas();
    ReservaResponse obtenerReserva(int id);
    List<NacionalidadResponse> obtenerNacionalidades();
    PaqueteResponse obtenerPaquetes();
}
