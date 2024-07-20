package com.ortega.admin.security;

import com.ortega.admin.models.entity.Empleados;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomEmpDetail implements UserDetails {

    private Empleados empleado;

    public CustomEmpDetail(Empleados empleado) {
        this.empleado = empleado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new CustomGrantedAuthority(empleado.getIdRol().getNombreRol()));
    }

    public String getFullname() {
        return empleado.getNombreApellidos();
    }

    public boolean getEstateAccount() { return empleado.getEstadoCuenta(); }

    @Override
    public String getPassword() {
        return empleado.getPassword();
    }

    @Override
    public String getUsername() {
        return empleado.getCorreo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return empleado.getEstadoCuenta();
    }
}
