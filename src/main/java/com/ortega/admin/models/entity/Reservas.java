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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Clientes idCliente;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_partida")
    private Date fechaPartida;

    @Column(name = "tipo_viaje", length = 30)
    private String tipoViaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private Estado idEstado;

    @Column(name = "num_pasajeros")
    private Integer numPasajeros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paquete")
    private Paquetes idPaquete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private Empleados idEmpleado;

    @Column(name = "costo_total")
    private Double costoTotal;

    @Column(name = "notas_adicionales", length = Integer.MAX_VALUE)
    private String notasAdicionales;

    @OneToMany(mappedBy = "idReserva")
    private Set<CostosTours> costosTours = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idReserva")
    private Set<Pasajeros> pasajeros = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idReserva")
    private Set<Transacciones> transacciones = new LinkedHashSet<>();

}