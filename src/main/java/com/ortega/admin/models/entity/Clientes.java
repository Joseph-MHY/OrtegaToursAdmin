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
@Table(name = "clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('clientes_id_cliente_seq'::regclass)")
    @Column(name = "id_cliente", nullable = false)
    private Integer id;

    @Column(name = "nombres", nullable = false, length = 80)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 80)
    private String apellidos;

    @Column(name = "correo", nullable = false, length = 80)
    private String correo;

    @Column(name = "celular", nullable = false, length = 9)
    private String celular;

    @Column(name = "numdocumento", nullable = false, length = 20)
    private String numdocumento;

    @OneToMany(mappedBy = "idCliente")
    private Set<Reservas> reservas = new LinkedHashSet<>();

}