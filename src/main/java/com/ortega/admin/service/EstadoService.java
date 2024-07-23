package com.ortega.admin.service;

import com.ortega.admin.models.DTO.EstadoDTO;

import java.util.Collection;

public interface EstadoService {

    public abstract Collection<EstadoDTO> getAllEstados();
}
