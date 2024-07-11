package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transacciones")
public class Transacciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('transacciones_id_transaccion_seq'::regclass)")
    @Column(name = "id_transaccion", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reservas idReserva;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_transaccion", nullable = false)
    private Date fechaTransaccion;

    @Column(name = "monto_a_pagar", nullable = false)
    private Double montoAPagar;

    @Column(name = "monto_pagado", nullable = false)
    private Double montoPagado;

    @Column(name = "referencia_pago", length = 50)
    private String referenciaPago;

    @ColumnDefault("'PENDIENTE'")
    @Column(name = "estado_pago", length = 10)
    private String estadoPago;

    @Column(name = "tipo_moneda", nullable = false, length = 3)
    private String tipoMoneda;

}