package com.ortega.admin.service;

import com.ortega.admin.models.DTO.EmpleadoRequest;
import com.ortega.admin.models.DTO.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;

import java.util.List;

public interface EmpService {

    Empleados save(EmpleadoRequest empleadoRequest);
    List<EmpleadoResponse> obtenerTodosLosEmpleados();
    EmpleadoResponse.EmpleadoUnitResponse obtenerEmpleadoPorId(Integer id);
}
