package com.ortega.admin.models.DTO.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReporteRequest {

    private Integer id_reserva;
    private String fecha_registro;
    private String cliente;
    private String numdocumento;
    private Integer num_pasajeros;
    private String nombre_paquete;
    private String categoria_paquete;
    private String tipo_viaje;
    private String conductor;
    private BigDecimal costo_total;
}
