package com.ortega.admin.models.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponse {

    private Integer id;
    private String nombreApellidos;
    private String numDocumento;
    private String puesto;
    private String telefono;
    private Double salario;
    private String estado;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmpleadoUnitResponse {
        private String nombreApellidos;
        private String direccion;
        private Integer idTipoDocumento;
        private String numDocumento;
        private String correo;
        private Date fechaNac;
        private Date fechaContratacion;
        private String telefono;
        private String contactoEmergencia;
        private Integer idRol;
        private Integer idTipoContrato;
        private String horarioTrabajo;
        private String cuentaBancaria;
        private Double salario;
        private String observaciones;
        private Boolean estadoCuenta;
    }
}
