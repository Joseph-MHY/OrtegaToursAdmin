package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('rol_id_rol_seq'::regclass)")
    @Column(name = "id_rol", nullable = false)
    private Integer id;

    @Column(name = "nombre_rol", nullable = false, length = 30)
    private String nombreRol;

    @OneToMany(mappedBy = "idRol")
    private Set<Empleados> empleados = new LinkedHashSet<>();

}