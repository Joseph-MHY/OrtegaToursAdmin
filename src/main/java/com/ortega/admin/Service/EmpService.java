package com.ortega.admin.service;

import com.ortega.admin.models.DTO.EmpleadoDTO;
import com.ortega.admin.models.entity.Empleados;

public interface EmpService {

    Empleados save(EmpleadoDTO empleadoDTO);
}
