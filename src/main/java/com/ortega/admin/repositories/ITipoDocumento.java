package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Tipodocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoDocumento extends JpaRepository<Tipodocumento, Integer> {
}
