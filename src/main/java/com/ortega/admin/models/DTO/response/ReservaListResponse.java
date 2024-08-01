package com.ortega.admin.models.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaListResponse {
    private Integer idReserva;
    private String cliente;
    private String numdocumento;
    private String celular;
    private String nombrePaquete;
    private String tipoViaje;
    private String nombreEstado;
    private Date fechaRegistro;
}
