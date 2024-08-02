package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.request.EmpleadoRequest;
import com.ortega.admin.models.DTO.response.ConductoresResponse;
import com.ortega.admin.models.DTO.response.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.service.IMPL.EmpServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class EmpleadosController {

    @Autowired
    private EmpServiceImpl empService;

    @PostMapping("/empleados/crear")
    public ResponseEntity<String> createEmpleado(@Valid @RequestBody EmpleadoRequest empleadoRequest) {
        return new ResponseEntity<>(empService.save(empleadoRequest), HttpStatus.CREATED);
    }

    @GetMapping("/empleados")
    public List<EmpleadoResponse> listarEmpleados() {
        return empService.obtenerTodosLosEmpleados();
    }

    @GetMapping("/empleados/{id}")
    public EmpleadoResponse.EmpleadoUnitResponse obtenerEmpleado(@PathVariable int id) {
        return empService.obtenerEmpleadoPorId(id);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<String> updateEmpleado(@PathVariable Integer id,
                                                    @RequestBody EmpleadoRequest.EmpleadoUpdateRequest empleadoUpdateDTO) {
        try {
            System.out.println(empleadoUpdateDTO);
            return new ResponseEntity<>(empService.updateEmpleado(id, empleadoUpdateDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear usuario Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/empleados/conductores")
    public List<ConductoresResponse> getConductores(){
        return empService.obtenerTodosLosConductores();
    }
}
