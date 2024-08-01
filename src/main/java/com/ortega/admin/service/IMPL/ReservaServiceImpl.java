package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.response.*;
import com.ortega.admin.models.entity.Nacionalidades;
import com.ortega.admin.repositories.INacionalidad;
import com.ortega.admin.repositories.IReserva;
import com.ortega.admin.service.IReservaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReserva iReserva;

    @Autowired
    private INacionalidad iNacionalidad;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ReporteResponse> listarReservasParaReporte() {
        List<Object[]> results = iReserva.findAllReservasForReport();
        List<ReporteResponse> reportes = new ArrayList<>();

        for (Object[] result : results) {
            ReporteResponse response = new ReporteResponse();
            response.setId_reserva((Integer) result[0]);
            response.setFecha_registro((Date) result[1]);
            response.setCliente((String) result[2]);
            response.setNumdocumento((String) result[3]);
            response.setNum_pasajeros((Integer) result[4]);
            response.setNombre_paquete((String) result[5]);
            response.setCategoria_paquete((String) result[6]);
            response.setTipo_viaje((String) result[7]);
            response.setConductor((String) result[8]);
            response.setCosto_total((BigDecimal) result[9]);
            reportes.add(response);
        }
        return reportes;
    }

    @Override
    public List<ReservaListResponse> listarReservas() {
        List<Object[]> results = iReserva.findReservas();
        List<ReservaListResponse> reservas = new ArrayList<>();

        for (Object[] result : results) {
            ReservaListResponse response = new ReservaListResponse();
            response.setIdReserva((Integer) result[0]);
            response.setCliente((String) result[1]);
            response.setNumdocumento((String) result[2]);
            response.setCelular((String) result[3]);
            response.setNombrePaquete((String) result[4]);
            response.setTipoViaje((String) result[5]);
            response.setNombreEstado((String) result[6]);
            response.setFechaRegistro((Date) result[7]);
            reservas.add(response);
        }
        return reservas;
    }

    @Override
    public ReservaResponse obtenerReserva(int idReserva) {
        ReservaResponse response = new ReservaResponse();

        // Obtener cliente
        Object[] clientDataArray = iReserva.getClientById(idReserva);
        if (clientDataArray != null && clientDataArray.length > 0) {
            Object[] clientData = (Object[]) clientDataArray[0]; // Acceder al array interno
            System.out.println("Client Data: " + Arrays.toString(clientData));

            ReservaResponse.Cliente cliente = new ReservaResponse.Cliente();
            cliente.setNombre((String) clientData[0]);
            cliente.setApellido((String) clientData[1]);
            cliente.setDocumento((String) clientData[2]);
            cliente.setCorreo((String) clientData[3]);
            cliente.setTelefono((String) clientData[4]);
            response.setCliente(cliente);
        }

        // Obtener detalles de la reserva
        Object[] reservaDetailsArray = iReserva.getReservaDetails(idReserva);
        if (reservaDetailsArray != null && reservaDetailsArray.length > 0) {
            Object[] reservaDetails = (Object[]) reservaDetailsArray[0]; // Acceder al array interno
            System.out.println("Reserva Details: " + Arrays.toString(reservaDetails));

            response.setFechaRegistro(reservaDetails[0] != null ? (Date) reservaDetails[0] : null);
            response.setFechaPartida(reservaDetails[1] != null ? (Date) reservaDetails[1] : null);
            response.setNombrePaquete(reservaDetails[2] != null ? reservaDetails[2].toString() : null);
            response.setTipoViaje(reservaDetails[3] != null ? reservaDetails[3].toString() : null);
            response.setConductor(reservaDetails[4] != null ? reservaDetails[4].toString() : null);
            response.setCostoBase(reservaDetails[5] != null ? (BigDecimal) reservaDetails[5] : null);
            response.setCostoFijo(reservaDetails[6] != null ? (BigDecimal) reservaDetails[6] : null);
            response.setCostoTotal(reservaDetails[7] != null ? (BigDecimal) reservaDetails[7] : null);
            response.setEstado(reservaDetails[8] != null ? reservaDetails[8].toString() : null);
            response.setNotas(reservaDetails[9] != null ? reservaDetails[9].toString() : null);
            response.setTipoMoneda(reservaDetails[10] != null ? reservaDetails[10].toString() : null);
        }

        // Obtener pasajeros
        List<Object[]> pasajerosDataArray = iReserva.getPassengersByReservation(idReserva);
        List<ReservaResponse.Pasajero> pasajeros = new ArrayList<>();
        for (Object[] data : pasajerosDataArray) {
            Object[] pasajeroData = data; // Asegurarse de acceder al array interno
            System.out.println("Pasajero Data: " + Arrays.toString(pasajeroData));

            ReservaResponse.Pasajero pasajero = new ReservaResponse.Pasajero();
            pasajero.setNombre(pasajeroData[0] != null ? pasajeroData[0].toString() : null);
            pasajero.setApellido(pasajeroData[1] != null ? pasajeroData[1].toString() : null);
            pasajero.setCorreo(pasajeroData[2] != null ? pasajeroData[2].toString() : null);
            pasajero.setCelular(pasajeroData[3] != null ? pasajeroData[3].toString() : null);
            pasajero.setNacionalidad(pasajeroData[4] != null ? pasajeroData[4].toString() : null);
            pasajero.setDocumento(pasajeroData[5] != null ? pasajeroData[5].toString() : null);
            pasajeros.add(pasajero);
        }
        response.setPasajeros(pasajeros);

        // Obtener costos adicionales
        List<Object[]> costosAdicionalesDataArray = iReserva.getCostosAdicionales(idReserva);
        List<ReservaResponse.CostoAdicional> costosAdicionales = new ArrayList<>();
        for (Object[] data : costosAdicionalesDataArray) {
            Object[] costoData = data; // Asegurarse de acceder al array interno
            System.out.println("Costo Adicional Data: " + Arrays.toString(costoData));

            ReservaResponse.CostoAdicional costo = new ReservaResponse.CostoAdicional();
            costo.setDescripcion(costoData[0] != null ? costoData[0].toString() : null);
            costo.setCosto(costoData[1] != null ? (BigDecimal) costoData[1] : null);
            costosAdicionales.add(costo);
        }
        response.setCostosAdicionales(costosAdicionales);

        return response;
    }

    @Override
    public List<NacionalidadResponse> obtenerNacionalidades() {
        List<Nacionalidades> nacionalidades = iNacionalidad.findAllOrderedByNombre();
        return nacionalidades.stream()
                .map(nacionalidad -> modelMapper.map(nacionalidad, NacionalidadResponse.class))
                .collect(Collectors.toList());
    }
}
