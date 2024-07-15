package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.EstadoDTO;
import com.ortega.admin.service.IMPL.EstadoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    @Autowired
    private EstadoServiceImpl estadoServiceImpl;

    @GetMapping("/estados")
    public Collection<EstadoDTO> getEstados() {
        return estadoServiceImpl.getAllEstados();
    }

}
