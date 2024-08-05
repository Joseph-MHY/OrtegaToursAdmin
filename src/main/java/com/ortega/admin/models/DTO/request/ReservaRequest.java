package com.ortega.admin.models.DTO.request;

import com.ortega.admin.Utils.TipoViaje;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservaRequest {

    @Data
    public static class Cliente {
        private String nombre;
        private String apellido;
        private String document;
        private String correo;
        private String telefono;
    }

    @Data
    @NoArgsConstructor
    public static class Pasajeros {
        private String nombres;
        private Integer id_reserva;
        private String apellidos;
        private Integer id_nacionalidad;
        private String num_documento;
        private String correo;
        private String celular;
    }

    @Data
    @NoArgsConstructor
    public static class Costos {
        private String descripcion;
        private Double monto;
        private Integer id_reserva;
    }

    @Data
    @NoArgsConstructor
    public static class Transaccion {
        private Integer id_reserva;
        private Date fecha_transaccion;
        private String estado_pago;
        private String tipo_moneda;
    }

    private Cliente cliente;
    private Date fecha_registro = new Date();
    private LocalDate fecha_partida;
    private TipoViaje tipo_viaje;
    private List<Pasajeros> pasajeros = new ArrayList<>();
    private Integer idEstado;
    private Integer num_pasajeros;
    private Integer idPaquete;
    private Integer idEmpleado;
    private List<Costos> costos = new ArrayList<>();
    private Double costo_total;
    private String notas_adicionales;
    private Transaccion transaccion;

    public void setPasajeros(List<Pasajeros> pasajeros) {
        this.pasajeros = pasajeros;
        this.num_pasajeros = pasajeros.size();
    }
}
