package com.ortega.admin.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados")
public class Empleados {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empleados_id_gen")
    @SequenceGenerator(name = "empleados_id_gen", sequenceName = "empleados_id_empleado_seq", allocationSize = 1)
    @Column(name = "id_empleado", nullable = false)
    private Integer id;

    @Column(name = "nombre_apellidos", nullable = false, length = 80)
    private String nombreApellidos;

    @Column(name = "direccion", nullable = false, length = 160)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private Tipodocumento idTipoDocumento;

    @Column(name = "num_documento", nullable = false, length = 20)
    private String numDocumento;

    @Column(name = "correo", nullable = false, length = 90)
    private String correo;

    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nac", nullable = false)
    private Date fechaNac;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_contratacion", nullable = false)
    private Date fechaContratacion;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "contacto_emergencia", length = 9)
    private String contactoEmergencia;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol idRol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_contrato", nullable = false)
    private Tipocontrato idTipoContrato;

    @Column(name = "horario_trabajo", nullable = false, length = 150)
    private String horarioTrabajo;

    @Column(name = "cuenta_bancaria", length = 40)
    private String cuentaBancaria;

    @Column(name = "salario", nullable = false)
    private Double salario;

    @Column(name = "observaciones", length = 200)
    private String observaciones;

    @Column(name = "estado_cuenta", nullable = false)
    private Boolean estadoCuenta;

    @OneToMany(mappedBy = "idEmpleado")
    private Set<Reservas> reservas = new LinkedHashSet<>();

}