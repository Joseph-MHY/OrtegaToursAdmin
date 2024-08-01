package com.ortega.admin.repositories;

import com.ortega.admin.models.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            "LEFT JOIN categoria_paquete cp ON p.id_categoria_paquete = cp.id_categoria_paquete " +
            "WHERE r.id_estado = 3",
            nativeQuery = true)
    List<Object[]> findAllReservasForReport();

    @Query(value = "SELECT r.id_reserva AS id_reserva, " +
            "c.nombres || ' ' || c.apellidos AS cliente, " +
            "c.numdocumento, c.celular, p.nombre_paquete AS nombre_paquete, " +
            "r.tipo_viaje AS tipo_viaje, e.nombre_estado AS nombre_estado, r.fecha_registro " +
            "FROM reservas r " +
            "JOIN clientes c ON r.id_cliente = c.id_cliente " +
            "JOIN paquetes p ON r.id_paquete = p.id_paquete " +
            "JOIN estado e ON r.id_estado = e.id_estado " ,
            nativeQuery = true)
    List<Object[]> findReservas();

    @Query(value = "SELECT * FROM get_data_client(:idReserva)", nativeQuery = true)
    Object[] getClientById(@Param("idReserva") Integer idReserva);

    @Query(value = "SELECT * FROM get_reserva_details(:idReserva)", nativeQuery = true)
    Object[] getReservaDetails(@Param("idReserva") Integer idReserva);

    @Query(value = "SELECT * FROM get_passengers_by_reservation(:idReserva)", nativeQuery = true)
    List<Object[]> getPassengersByReservation(@Param("idReserva") Integer idReserva);

    @Query(value = "SELECT co.descripcion, co.monto " +
            "FROM reservas r " +
            "JOIN costos_tours co ON r.id_reserva = co.id_reserva " +
            "WHERE r.id_reserva = :idReserva", nativeQuery = true)
    List<Object[]> getCostosAdicionales(@Param("idReserva") Integer idReserva);
}
