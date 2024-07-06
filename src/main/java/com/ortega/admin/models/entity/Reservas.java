package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservas")
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('reservas_id_reserva_seq'::regclass)")
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes idCliente;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_partida", nullable = false)
    private Date fechaPartida;

    @Column(name = "tipo_viaje", nullable = false, length = 30)
    private String tipoViaje;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado idEstado;

    @Column(name = "num_pasajeros", nullable = false)
    private Integer numPasajeros;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_paquete", nullable = false)
    private Paquetes idPaquete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private Empleados idEmpleado;

    @Column(name = "costo_total", nullable = false)
    private Double costoTotal;

    @Column(name = "notas_adicionales", length = 150)
    private String notasAdicionales;

    @OneToMany(mappedBy = "idReserva")
    private Set<Pasajeros> pasajeros = new LinkedHashSet<>();

}