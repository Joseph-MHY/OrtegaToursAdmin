package com.ortega.admin.models.DTO;

import com.ortega.admin.models.entity.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {

    private int id;
    private String nombreEstado;

    // Método para convertir de Estado a EstadoDTO
    public static EstadoDTO fromEstado(Estado estado) {
        return new EstadoDTO(estado.getId(), estado.getNombreEstado());
    }

    // Método para convertir de EstadoDTO a Estado
    public Estado toEstado() {
        Estado estado = new Estado();
        estado.setId(this.id);
        estado.setNombreEstado(this.nombreEstado);
        return estado;
    }
}
