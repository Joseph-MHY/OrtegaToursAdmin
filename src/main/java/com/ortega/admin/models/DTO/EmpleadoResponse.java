package com.ortega.admin.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
