package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Pasajeros;
import com.ortega.admin.models.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPasajero extends JpaRepository<Pasajeros, Integer> {

    void deleteAllByIdReserva(Reservas idReserva);
}
