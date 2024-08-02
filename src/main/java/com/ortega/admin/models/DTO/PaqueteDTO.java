package com.ortega.admin.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaqueteDTO {
    private Integer id;
    private String nombre_paquete;
    private Double precio_base;
    private Double precio_fijo;
}
