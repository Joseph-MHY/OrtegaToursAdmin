package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleado extends JpaRepository<Empleados, Integer> {

    Empleados findByCorreo(String correo);
}
