package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstado extends JpaRepository<Estado, Integer> {
}
