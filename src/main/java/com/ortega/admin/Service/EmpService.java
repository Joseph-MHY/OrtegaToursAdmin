package com.ortega.admin.service;

import com.ortega.admin.models.DTO.EmpleadoRequest;
import com.ortega.admin.models.entity.Empleados;

public interface EmpService {

    Empleados save(EmpleadoRequest empleadoRequest);
}
