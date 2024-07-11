package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "costos_tours")
public class CostosTours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('costos_tours_id_costo_seq'::regclass)")
    @Column(name = "id_costo", nullable = false)
    private Integer id;

    @Column(name = "descripcion", nullable = false, length = 40)
    private String descripcion;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva")
    private Reservas idReserva;

}