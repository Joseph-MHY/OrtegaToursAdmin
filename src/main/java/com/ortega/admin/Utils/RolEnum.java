package com.ortega.admin.Utils;

public enum RolEnum {
    ROLE_ADMIN("Administrador"),
    ROLE_ATTENTION("Atención al cliente"),
    ROLE_CONDUCTOR("Conductor");

    private final String descripcion;

    RolEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static RolEnum fromNombreRol(String nombreRol) {
        for (RolEnum rolEnum : values()) {
            if (rolEnum.name().equals(nombreRol)) {
                return rolEnum;
            }
        }
        throw new IllegalArgumentException("Nombre de rol desconocido: " + nombreRol);
    }
}

