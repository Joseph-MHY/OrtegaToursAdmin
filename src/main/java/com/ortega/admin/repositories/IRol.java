package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRol extends JpaRepository<Rol, Integer> {
}
