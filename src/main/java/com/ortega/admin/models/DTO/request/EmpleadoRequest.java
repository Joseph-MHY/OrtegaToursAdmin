package com.ortega.admin.models.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.models.entity.Rol;
import com.ortega.admin.models.entity.Tipocontrato;
import com.ortega.admin.models.entity.Tipodocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoRequest {

    @NotBlank(message = "El nombre y apellidos son obligatorios")
    @Size(max = 80, message = "El nombre y apellidos no pueden exceder los 80 caracteres")
    private String nombreApellidos;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 160, message = "La dirección no puede exceder los 160 caracteres")
    private String direccion;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no puede exceder los 20 caracteres")
    private String numDocumento;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 90, message = "El correo no puede exceder los 90 caracteres")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 5, max = 20, message = "La contraseña debe tener entre 5 y 20 caracteres")
    private String password;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNac;

    private Date fechaContratacion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @Size(min = 9, max = 9, message = "El contacto de emergencia debe tener 9 dígitos")
    private String contactoEmergencia; // Opcional

    @NotNull(message = "El rol es obligatorio")
    private Integer idRol;

    @NotNull(message = "El tipo de contrato es obligatorio")
    private Integer idTipoContrato;

    @NotBlank(message = "El horario de trabajo es obligatorio")
    @Size(max = 150, message = "El horario de trabajo no puede exceder los 150 caracteres")
    private String horarioTrabajo;
    private String cuentaBancaria; // Opcional

    @NotNull(message = "El salario es obligatorio")
    private Double salario;
    private String observaciones; // Opcional
    private Boolean estadoCuenta;

    // Método para convertir DTO a Entidad
    public Empleados toEntity(Tipodocumento tipoDocumento, Rol rol, Tipocontrato tipoContrato, String passwordEMP) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = sdf.format(new Date());
        try {
            Date fechaDesdeString = sdf.parse(fechaFormateada);
            return Empleados.builder()
                    .id(null)
                    .nombreApellidos(this.nombreApellidos)
                    .direccion(this.direccion)
                    .idTipoDocumento(tipoDocumento)
                    .numDocumento(this.numDocumento)
                    .correo(this.correo)
                    .password(passwordEMP)
                    .fechaNac(this.fechaNac)
                    .fechaContratacion(fechaDesdeString)
                    .telefono(this.telefono)
                    .contactoEmergencia(this.contactoEmergencia)
                    .idRol(rol)
                    .idTipoContrato(tipoContrato)
                    .horarioTrabajo(this.horarioTrabajo)
                    .cuentaBancaria(this.cuentaBancaria)
                    .salario(this.salario)
                    .observaciones(this.observaciones)
                    .estadoCuenta(true)
                    .build();
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Data
    public static class EmpleadoUpdateRequest {
        @NotBlank(message = "El nombre y apellidos son obligatorios")
        @Size(max = 80, message = "El nombre y apellidos no pueden exceder los 80 caracteres")
        private String nombreApellidos;

        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 160, message = "La dirección no puede exceder los 160 caracteres")
        private String direccion;

        @NotNull(message = "El tipo de documento es obligatorio")
        private Integer idTipoDocumento;

        @NotBlank(message = "El número de documento es obligatorio")
        @Size(max = 20, message = "El número de documento no puede exceder los 20 caracteres")
        private String numDocumento;

        @Email(message = "Correo inválido")
        @NotBlank(message = "El correo es obligatorio")
        @Size(max = 90, message = "El correo no puede exceder los 90 caracteres")
        private String correo;

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate fechaNac;

        private LocalDate fechaContratacion;

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
        private String telefono;

        @Size(min = 9, max = 9, message = "El contacto de emergencia debe tener 9 dígitos")
        private String contactoEmergencia;

        @NotNull(message = "El rol es obligatorio")
        private Integer idRol;

        @NotNull(message = "El tipo de contrato es obligatorio")
        private Integer idTipoContrato;

        @NotBlank(message = "El horario de trabajo es obligatorio")
        @Size(max = 150, message = "El horario de trabajo no puede exceder los 150 caracteres")
        private String horarioTrabajo;

        private String cuentaBancaria;

        @NotNull(message = "El salario es obligatorio")
        private Double salario;

        private String observaciones;

        private Boolean estadoCuenta;
    }
}
