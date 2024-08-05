package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICliente extends JpaRepository<Clientes, Integer> {

    Clientes findByNumdocumento(String numdocumento);
    Clientes findByCorreo(String correo);
}
