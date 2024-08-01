package com.ortega.admin.models.DTO.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ReservaResponse {

    @Data
    public static class Cliente {
        private String nombre;
        private String apellido;
        private String documento;
        private String correo;
        private String telefono;
    }

    @Data
    public static class Pasajero {
        private String nombre;
        private String apellido;
        private String correo = null;
        private String celular = null;
        private String nacionalidad;
        private String documento;
    }

    @Data
    public static class CostoAdicional {
        private String descripcion;
        private BigDecimal costo;
    }

    private Cliente cliente;
    private Date fechaRegistro;
    private Date fechaPartida;
    private String nombrePaquete;
    private String tipoViaje;
    private String conductor;
    private List<Pasajero> pasajeros;
    private BigDecimal costoBase;
    private BigDecimal costoFijo;
    private List<CostoAdicional> costosAdicionales;
    private BigDecimal costoTotal;
    private String estado;
    private String notas;
    private String tipoMoneda;
}
