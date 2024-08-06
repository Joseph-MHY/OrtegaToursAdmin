package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Reservas;
import com.ortega.admin.models.entity.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransaccion extends JpaRepository<Transacciones, Integer> {

    Transacciones findByIdReserva(Reservas idReserva);
}
