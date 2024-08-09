package com.ortega.admin.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tipodocumento")
public class Tipodocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('tipodocumento_id_tipo_documento_seq'::regclass)")
    @Column(name = "id_tipo_documento", nullable = false)
    private Integer id;

    @Column(name = "nombre_documento", nullable = false, length = 30)
    private String nombreDocumento;

    @JsonIgnore
    @OneToMany(mappedBy = "idTipoDocumento")
    private Set<Empleados> empleados = new LinkedHashSet<>();

}