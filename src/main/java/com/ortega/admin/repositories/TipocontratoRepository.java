package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Tipocontrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipocontratoRepository extends JpaRepository<Tipocontrato, Integer> {
}
