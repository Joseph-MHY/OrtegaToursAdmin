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
@Table(name = "estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('estado_id_estado_seq'::regclass)")
    @Column(name = "id_estado", nullable = false)
    private Integer id;

    @Column(name = "nombre_estado", nullable = false, length = 40)
    private String nombreEstado;

    @OneToMany(mappedBy = "idEstado")
    private Set<Reservas> reservas = new LinkedHashSet<>();

}