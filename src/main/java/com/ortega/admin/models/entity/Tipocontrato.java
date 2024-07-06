package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tipocontrato")
public class Tipocontrato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipocontrato_id_gen")
    @SequenceGenerator(name = "tipocontrato_id_gen", sequenceName = "tipocontrato_id_tipo_contrato_seq", allocationSize = 1)
    @Column(name = "id_tipo_contrato", nullable = false)
    private Integer id;

    @Column(name = "nombre_contrato", nullable = false, length = 30)
    private String nombreContrato;

    @OneToMany(mappedBy = "idTipoContrato")
    private Set<Empleados> empleados = new LinkedHashSet<>();

}