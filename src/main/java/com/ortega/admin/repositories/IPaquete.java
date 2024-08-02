package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.CategoriaPaquete;
import com.ortega.admin.models.entity.Paquetes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaquete extends JpaRepository<Paquetes, Integer> {
    List<Paquetes> findByIdCategoriaPaquete(CategoriaPaquete categoriaPaquete);
}
