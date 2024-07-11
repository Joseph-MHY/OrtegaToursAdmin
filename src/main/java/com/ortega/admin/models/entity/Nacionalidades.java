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
@Table(name = "nacionalidades")
public class Nacionalidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('nacionalidades_id_nacionalidad_seq'::regclass)")
    @Column(name = "id_nacionalidad", nullable = false)
    private Integer id;

    @Column(name = "nombre_nacionalidad", nullable = false, length = 50)
    private String nombreNacionalidad;

    @OneToMany(mappedBy = "idNacionalidad")
    private Set<Pasajeros> pasajeros = new LinkedHashSet<>();

}