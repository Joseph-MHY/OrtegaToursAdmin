package com.ortega.admin.repositories;

import com.ortega.admin.models.DTO.ReporteResponse;
import com.ortega.admin.models.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReserva extends JpaRepository<Reservas, Integer> {

    @Query(value = "SELECT " +
            "r.id_reserva AS idReserva, " +
            "r.fecha_registro AS fechaRegistro," +
            "c.nombres || ' ' || c.apellidos AS cliente, " +
            "c.numdocumento AS numDocumento, " +
            "r.num_pasajeros AS numPasajeros, " +
            "p.nombre_paquete AS nombrePaquete, " +
            "cp.nombre_categoria AS categoriaPaquete," +
            "r.tipo_viaje AS tipoViaje, " +
            "e.nombre_apellidos AS conductor, " +
            "r.costo_total AS costoTotal " +
            "FROM reservas r " +
            "LEFT JOIN clientes c ON r.id_cliente = c.id_cliente " +
            "LEFT JOIN paquetes p ON r.id_paquete = p.id_paquete " +
            "LEFT JOIN empleados e ON r.id_empleado = e.id_empleado " +
            "LEFT JOIN categoria_paquete cp ON p.id_categoria_paquete = cp.id_categoria_paquete",
            nativeQuery = true)
    List<Object[]> findAllReservasForReport();
}
