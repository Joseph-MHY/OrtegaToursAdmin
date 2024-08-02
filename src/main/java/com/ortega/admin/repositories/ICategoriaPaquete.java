package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.CategoriaPaquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaPaquete extends JpaRepository<CategoriaPaquete, Integer> {
}
