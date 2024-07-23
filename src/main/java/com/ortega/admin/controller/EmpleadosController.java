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

    @PostMapping("/empleados/crear")
    public ResponseEntity<Empleados> createEmpleado(@Valid @RequestBody EmpleadoRequest empleadoRequest) {
        Empleados empleado = empService.save(empleadoRequest);
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
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
        } catch (RuntimeException e) {
            // En caso de que el empleado o entidades relacionadas no se encuentren
            return new ResponseEntity<>("Error al crear usuario RuntimeException: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Para cualquier otro tipo de error
            return new ResponseEntity<>("Error al crear usuario Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
