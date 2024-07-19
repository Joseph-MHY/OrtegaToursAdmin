package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.EmpleadoRequest;
import com.ortega.admin.models.DTO.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.service.IMPL.EmpServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class EmpleadosController {

    @Autowired
    private EmpServiceImpl empService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/empleados/crear")
    public ResponseEntity<Empleados> createEmpleado(@Valid @RequestBody EmpleadoRequest empleadoRequest) {
        Empleados empleado = empService.save(empleadoRequest);
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
    }

    @GetMapping("/empleados")
    public List<EmpleadoResponse> listarEmpleados() {
        return empService.obtenerTodosLosEmpleados();
    }

}
