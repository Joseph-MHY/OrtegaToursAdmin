package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tipodocumento")
public class Tipodocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipodocumento_id_gen")
    @SequenceGenerator(name = "tipodocumento_id_gen", sequenceName = "tipodocumento_id_tipo_documento_seq", allocationSize = 1)
    @Column(name = "id_tipo_documento", nullable = false)
    private Integer id;

    @Column(name = "nombre_documento", nullable = false, length = 30)
    private String nombreDocumento;

    @OneToMany(mappedBy = "idTipoDocumento")
    private Set<Empleados> empleados = new LinkedHashSet<>();

}