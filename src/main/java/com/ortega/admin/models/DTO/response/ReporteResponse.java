package com.ortega.admin.models.DTO.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReporteResponse {

    private Integer id_reserva;
    private Date fecha_registro;
    private String cliente;
    private String numdocumento;
    private Integer num_pasajeros;
    private String nombre_paquete;
    private String categoria_paquete;
    private String tipo_viaje;
    private String conductor;
    private BigDecimal costo_total;
}
