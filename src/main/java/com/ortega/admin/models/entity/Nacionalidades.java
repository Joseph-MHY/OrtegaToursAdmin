package com.ortega.admin.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    public Nacionalidades(Integer id, String nombreNacionalidad) {
        this.id = id;
        this.nombreNacionalidad = nombreNacionalidad;
    }

    public Nacionalidades() {

    }
}