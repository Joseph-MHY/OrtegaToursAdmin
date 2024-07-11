package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "pasajeros")
public class Pasajeros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('pasajeros_idpasajero_seq'::regclass)")
    @Column(name = "idpasajero", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reservas idReserva;

    @Column(name = "nombres", nullable = false, length = 80)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 80)
    private String apellidos;

    @Column(name = "correo", length = 80)
    private String correo;

    @Column(name = "celular", length = 9)
    private String celular;

    @Column(name = "num_documento", nullable = false, length = 20)
    private String numDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nacionalidad")
    private Nacionalidades idNacionalidad;

}