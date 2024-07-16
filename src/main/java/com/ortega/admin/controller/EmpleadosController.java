package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.EmpleadoRequest;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.service.IMPL.EmpServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actions")
public class EmpleadosController {

    @Autowired
    private EmpServiceImpl empService;

    @PostMapping("/empleados")
    public ResponseEntity<Empleados> createEmpleado(@Valid @RequestBody EmpleadoRequest empleadoRequest) {
        Empleados empleado = empService.save(empleadoRequest);
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
    }

}
