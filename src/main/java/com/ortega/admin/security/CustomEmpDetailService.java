package com.ortega.admin.security;

import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomEmpDetailService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Empleados empleado = empleadoRepository.findByCorreo(correo);

        if (empleado == null){
            throw new UsernameNotFoundException("El correo no existe");
        }
        return new CustomEmpDetail(empleado);
    }
}
