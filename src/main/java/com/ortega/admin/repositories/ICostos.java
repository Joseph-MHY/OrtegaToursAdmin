package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.CostosTours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICostos extends JpaRepository<CostosTours, Integer> {
}
