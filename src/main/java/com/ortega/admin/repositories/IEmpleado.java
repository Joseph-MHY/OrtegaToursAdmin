package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmpleado extends JpaRepository<Empleados, Integer> {

    Empleados findByCorreo(String correo);

    @Query(value = "SELECT id_empleado, nombre_apellidos FROM empleados WHERE id_rol = 3;", nativeQuery = true)
    List<Object[]> getEmpleadosConductores();
}
