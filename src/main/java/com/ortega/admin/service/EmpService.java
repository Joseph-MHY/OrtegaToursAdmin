package com.ortega.admin.service;

import com.ortega.admin.models.DTO.request.EmpleadoRequest;
import com.ortega.admin.models.DTO.response.ConductoresResponse;
import com.ortega.admin.models.DTO.response.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;

import java.util.List;

public interface EmpService {

    String save(EmpleadoRequest empleadoRequest);
    List<EmpleadoResponse> obtenerTodosLosEmpleados();
    EmpleadoResponse.EmpleadoUnitResponse obtenerEmpleadoPorId(Integer id);
    String updateEmpleado(Integer id, EmpleadoRequest.EmpleadoUpdateRequest empleadoUpdateDTO);
    List<ConductoresResponse> obtenerTodosLosConductores();
}
