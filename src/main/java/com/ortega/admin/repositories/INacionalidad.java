package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Nacionalidades;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INacionalidad extends JpaRepository<Nacionalidades, Integer> {

    @Query("SELECT n FROM Nacionalidades n ORDER BY n.nombreNacionalidad ASC")
    List<Nacionalidades> findAllOrderedByNombre();
}
