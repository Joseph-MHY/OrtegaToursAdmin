package com.ortega.admin.models.DTO.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ReservaResponse {

    @Data
    public static class Cliente {
        private String nombres;
        private String apellidos;
        private String numdocumento;
        private String correo;
        private String celular;
    }

    @Data
    public static class Pasajero {
        private String nombres;
        private String apellidos;
        private String correo;
        private String celular;
        private Nacionalidad nacionalidad;
        private String num_documento;

        @Data
        public static class Nacionalidad {
            private Integer id_nacionalidad;
            private String nombre_nacionalidad;
        }
    }

    @Data
    public static class CostoAdicional {
        private String descripcion;
        private BigDecimal monto;
    }

    @Data
    public static class Transaccion {
        private Integer id_transaccion;
        private Date fecha_transaccion;
        private BigDecimal monto_pagado;
        private String estado_pago;
        private String tipo_moneda;
    }

    private Cliente cliente;
    private Date fecha_registro;
    private Date fecha_partida;
    private Integer id_paquete;
    private String tipo_viaje;
    private Integer id_conductor;
    private List<Pasajero> pasajeros;
    private BigDecimal costo_base;
    private BigDecimal costo_fijo;
    private List<CostoAdicional> costosAdicionales;
    private BigDecimal costo_total;
    private Integer id_estado;
    private String nombre_estado;
    private String notas_adicionales;
    private String tipo_moneda;
    private Transaccion transaccion;
}

