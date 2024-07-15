package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.EstadoDTO;
import com.ortega.admin.models.entity.Estado;
import com.ortega.admin.repositories.EstadoRepository;
import com.ortega.admin.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public Collection<EstadoDTO> getAllEstados() {
        List<Estado> estados = estadoRepository.findAll();
        return estados.stream()
                .map(EstadoDTO::fromEstado)
                .collect(Collectors.toList());
    }
}
