package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.PaqueteDTO;
import com.ortega.admin.models.DTO.request.ReservaRequest;
import com.ortega.admin.models.DTO.response.*;
import com.ortega.admin.models.entity.*;
import com.ortega.admin.repositories.*;
import com.ortega.admin.service.IReservaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements IReservaService {

    private IReserva iReserva;
    private INacionalidad iNacionalidad;
    private IPaquete iPaquete;
    private ICategoriaPaquete iCategoriaPaquete;
    private ICliente iCliente;
    private IPasajero iPasajero;
    private ICostos iCostos;
    private ITransaccion iTransaccion;
    private IEstado iEstado;
    private IEmpleado iEmpleado;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ReservaServiceImpl(ITransaccion iTransaccion, ICostos iCostos, IPasajero iPasajero, ICliente iCliente,
                              ICategoriaPaquete iCategoriaPaquete, IPaquete iPaquete, INacionalidad iNacionalidad,
                              IReserva iReserva, IEstado iEstado, IEmpleado iEmpleado) {
        this.iTransaccion = iTransaccion;
        this.iCostos = iCostos;
        this.iPasajero = iPasajero;
        this.iCliente = iCliente;
        this.iCategoriaPaquete = iCategoriaPaquete;
        this.iPaquete = iPaquete;
        this.iNacionalidad = iNacionalidad;
        this.iReserva = iReserva;
        this.iEstado = iEstado;
        this.iEmpleado = iEmpleado;
    }

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
            ReservaResponse.Cliente cliente = new ReservaResponse.Cliente();

            cliente.setNombres((String) clientData[0]);
            cliente.setApellidos((String) clientData[1]);
            cliente.setNumdocumento((String) clientData[2]);
            cliente.setCorreo((String) clientData[3]);
            cliente.setCelular((String) clientData[4]);
            response.setCliente(cliente);
        }

        // Obtener transaccion
        Object[] transactionDataArray = iReserva.getTransaccion(idReserva);
        if (transactionDataArray != null && transactionDataArray.length > 0) {
            Object[] transactionData = (Object[]) transactionDataArray[0];
            ReservaResponse.Transaccion transaccion = new ReservaResponse.Transaccion();

            transaccion.setId_transaccion((Integer) transactionData[0]);
            transaccion.setFecha_transaccion((Date) transactionData[1]);
            transaccion.setMonto_pagado((BigDecimal) transactionData[2]);
            transaccion.setEstado_pago((String) transactionData[3]);
            transaccion.setTipo_moneda((String) transactionData[4]);
            response.setTransaccion(transaccion);
        }

        // Obtener detalles de la reserva
        Object[] reservaDetailsArray = iReserva.getReservaDetails(idReserva);
        if (reservaDetailsArray != null && reservaDetailsArray.length > 0) {
            Object[] reservaDetails = (Object[]) reservaDetailsArray[0]; // Acceder al array interno

            response.setFecha_registro(reservaDetails[0] != null ? (Date) reservaDetails[0] : null);
            response.setFecha_partida(reservaDetails[1] != null ? (Date) reservaDetails[1] : null);
            response.setId_paquete(reservaDetails[2] != null ? (Integer) reservaDetails[2] : null);
            response.setTipo_viaje(reservaDetails[3] != null ? reservaDetails[3].toString() : null);
            response.setId_conductor(reservaDetails[4] != null ? (Integer) reservaDetails[4] : null);
            response.setCosto_base(reservaDetails[5] != null ? (BigDecimal) reservaDetails[5] : null);
            response.setCosto_fijo(reservaDetails[6] != null ? (BigDecimal) reservaDetails[6] : null);
            response.setCosto_total(reservaDetails[7] != null ? (BigDecimal) reservaDetails[7] : null);
            response.setId_estado(reservaDetails[8] != null ? (Integer) reservaDetails[8] : null);
            response.setNombre_estado(reservaDetails[9] != null ? reservaDetails[9].toString() : null);
            response.setNotas_adicionales(reservaDetails[10] != null ? reservaDetails[10].toString() : null);
            response.setTipo_moneda(reservaDetails[11] != null ? reservaDetails[11].toString() : null);
        }

        // Obtener pasajeros
        List<Object[]> pasajerosDataArray = iReserva.getPassengersByReservation(idReserva);
        List<ReservaResponse.Pasajero> pasajeros = new ArrayList<>();
        for (Object[] data : pasajerosDataArray) {
            Object[] pasajeroData = data;

            ReservaResponse.Pasajero.Nacionalidad nacionalidad = new ReservaResponse.Pasajero.Nacionalidad();
            ReservaResponse.Pasajero pasajero = new ReservaResponse.Pasajero();

            pasajero.setNombres(pasajeroData[0] != null ? pasajeroData[0].toString() : null);
            pasajero.setApellidos(pasajeroData[1] != null ? pasajeroData[1].toString() : null);
            pasajero.setCorreo(pasajeroData[2] != null ? pasajeroData[2].toString() : null);
            pasajero.setCelular(pasajeroData[3] != null ? pasajeroData[3].toString() : null);
            nacionalidad.setId_nacionalidad(pasajeroData[4] != null ? (Integer) pasajeroData[4] : null);
            nacionalidad.setNombre_nacionalidad(pasajeroData[5] != null ? pasajeroData[5].toString() : null);
            pasajero.setNacionalidad(nacionalidad);
            pasajero.setNum_documento(pasajeroData[6] != null ? pasajeroData[6].toString() : null);
            pasajeros.add(pasajero);
        }
        response.setPasajeros(pasajeros);

        // Obtener costos adicionales
        List<Object[]> costosAdicionalesDataArray = iReserva.getCostosAdicionales(idReserva);
        List<ReservaResponse.CostoAdicional> costosAdicionales = new ArrayList<>();
        for (Object[] data : costosAdicionalesDataArray) {
            Object[] costoData = data; // Asegurarse de acceder al array interno

            ReservaResponse.CostoAdicional costo = new ReservaResponse.CostoAdicional();
            costo.setDescripcion(costoData[0] != null ? costoData[0].toString() : null);
            costo.setMonto(costoData[1] != null ? (BigDecimal) costoData[1] : null);
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

    @Override
    public PaqueteResponse obtenerPaquetes() {
        CategoriaPaquete tourCategoria = iCategoriaPaquete.findById(1).orElse(null);
        CategoriaPaquete promoCategoria = iCategoriaPaquete.findById(2).orElse(null);

        List<PaqueteDTO> tours = iPaquete.findByIdCategoriaPaquete(tourCategoria).stream()
                .map(paquete -> new PaqueteDTO(
                        paquete.getId(),
                        paquete.getNombrePaquete(),
                        paquete.getCostoBase(),
                        paquete.getCostoFijo()
                ))
                .collect(Collectors.toList());

        List<PaqueteDTO> promociones = iPaquete.findByIdCategoriaPaquete(promoCategoria).stream()
                .map(paquete -> new PaqueteDTO(
                        paquete.getId(),
                        paquete.getNombrePaquete(),
                        paquete.getCostoBase(),
                        paquete.getCostoFijo()
                ))
                .collect(Collectors.toList());

        return new PaqueteResponse(tours, promociones);
    }

    @Transactional
    @Override
    public String registrarReserva(ReservaRequest reservaRequest) {

        // Buscar el cliente por número de documento
        Optional<Clientes> optionalClienteDocument = Optional.ofNullable(iCliente.findByNumdocumento(
                reservaRequest.getCliente().getDocument())
        );
        Optional<Clientes> optionalClienteCorreo = Optional.ofNullable(iCliente.findByCorreo(
                reservaRequest.getCliente().getCorreo())
        );

        Clientes cliente;

        if (optionalClienteDocument.isPresent()) {
            cliente = optionalClienteDocument.get();
        } else if (optionalClienteCorreo.isPresent()) {
            cliente = optionalClienteCorreo.get();
        } else {
            // Cliente no encontrado, se crea uno nuevo
            cliente = new Clientes();
            cliente.setNombres(reservaRequest.getCliente().getNombre());
            cliente.setApellidos(reservaRequest.getCliente().getApellido());
            cliente.setCorreo(reservaRequest.getCliente().getCorreo());
            cliente.setCelular(reservaRequest.getCliente().getTelefono());
            cliente.setNumdocumento(reservaRequest.getCliente().getDocument());
            cliente = iCliente.save(cliente);
        }

        Reservas reserva = new Reservas();
        reserva.setIdCliente(cliente);
        reserva.setFechaRegistro(new Date());
        reserva.setFechaPartida(localDateToDate(reservaRequest.getFecha_partida()));
        reserva.setTipoViaje(reservaRequest.getTipo_viaje().name());
        reserva.setIdEstado(obtenerEntidadPorId(iEstado, reservaRequest.getIdEstado(), "Estado"));
        reserva.setNumPasajeros(reservaRequest.getNum_pasajeros());
        reserva.setIdPaquete(obtenerEntidadPorId(iPaquete, reservaRequest.getIdPaquete(), "Paquete"));
        reserva.setIdEmpleado(obtenerEntidadPorId(iEmpleado, reservaRequest.getIdEmpleado(), "Empleado"));
        reserva.setCostoTotal(reservaRequest.getCosto_total());
        reserva.setNotasAdicionales(reservaRequest.getNotas_adicionales());

        reserva = iReserva.save(reserva);

        for (ReservaRequest.Pasajeros pasajeroRequest : reservaRequest.getPasajeros()) {
            Pasajeros pasajero = new Pasajeros();
            pasajero.setIdReserva(reserva);
            pasajero.setNombres(pasajeroRequest.getNombres());
            pasajero.setApellidos(pasajeroRequest.getApellidos());
            pasajero.setCorreo(pasajeroRequest.getCorreo());
            pasajero.setCelular(pasajeroRequest.getCelular());
            pasajero.setNumDocumento(pasajeroRequest.getNum_documento());
            pasajero.setIdNacionalidad(obtenerEntidadPorId(iNacionalidad, pasajeroRequest.getId_nacionalidad(), "Nacionalidad"));
            iPasajero.save(pasajero);
        }

        if (reservaRequest.getCostos() != null && !reservaRequest.getCostos().isEmpty()) {
            for (ReservaRequest.Costos costoRequest : reservaRequest.getCostos()) {
                CostosTours costo = new CostosTours();
                costo.setIdReserva(reserva);
                costo.setDescripcion(costoRequest.getDescripcion());
                costo.setMonto(costoRequest.getMonto());
                iCostos.save(costo);
            }
        }
        Transacciones transaccion = new Transacciones();
        transaccion.setIdReserva(reserva);
        transaccion.setFechaTransaccion(new Date());
        transaccion.setMontoPagado(reservaRequest.getCosto_total());
        transaccion.setEstadoPago(reservaRequest.getTransaccion().getEstado_pago());
        transaccion.setTipoMoneda(reservaRequest.getTransaccion().getTipo_moneda());
        iTransaccion.save(transaccion);

        return "Reserva registrada exitosamente";
    }

    @Transactional
    @Override
    public String actualizarReserva(Integer idReserva, ReservaRequest reservaRequest) {
        // Buscar la reserva existente por ID
        Reservas reservaExistente = iReserva.findById(idReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));

        // Buscar o actualizar el cliente
        Clientes clienteExistente = reservaExistente.getIdCliente();
        Optional<Clientes> optionalClienteDocument = Optional.ofNullable(iCliente.findByNumdocumento(reservaRequest.getCliente().getDocument()));
        Optional<Clientes> optionalClienteCorreo = Optional.ofNullable(iCliente.findByCorreo(reservaRequest.getCliente().getCorreo()));

        if (optionalClienteDocument.isPresent()) {
            clienteExistente = optionalClienteDocument.get();
        } else if (optionalClienteCorreo.isPresent()) {
            clienteExistente = optionalClienteCorreo.get();
        } else {
            clienteExistente.setNombres(reservaRequest.getCliente().getNombre());
            clienteExistente.setApellidos(reservaRequest.getCliente().getApellido());
            clienteExistente.setCorreo(reservaRequest.getCliente().getCorreo());
            clienteExistente.setCelular(reservaRequest.getCliente().getTelefono());
            clienteExistente.setNumdocumento(reservaRequest.getCliente().getDocument());
            clienteExistente = iCliente.save(clienteExistente);
        }

        // Actualizar la reserva
        reservaExistente.setFechaPartida(localDateToDate(reservaRequest.getFecha_partida()));
        reservaExistente.setTipoViaje(reservaRequest.getTipo_viaje().name());
        reservaExistente.setIdEstado(obtenerEntidadPorId(iEstado, reservaRequest.getIdEstado(), "Estado"));
        reservaExistente.setNumPasajeros(reservaRequest.getNum_pasajeros());
        reservaExistente.setIdPaquete(obtenerEntidadPorId(iPaquete, reservaRequest.getIdPaquete(), "Paquete"));
        reservaExistente.setIdEmpleado(obtenerEntidadPorId(iEmpleado, reservaRequest.getIdEmpleado(), "Empleado"));
        reservaExistente.setCostoTotal(reservaRequest.getCosto_total());
        reservaExistente.setNotasAdicionales(reservaRequest.getNotas_adicionales());

        iReserva.save(reservaExistente);

        // Eliminar y volver a agregar los pasajeros
        iPasajero.deleteAllByIdReserva(reservaExistente);
        for (ReservaRequest.Pasajeros pasajeroRequest : reservaRequest.getPasajeros()) {
            Pasajeros pasajero = new Pasajeros();
            pasajero.setIdReserva(reservaExistente);
            pasajero.setNombres(pasajeroRequest.getNombres());
            pasajero.setApellidos(pasajeroRequest.getApellidos());
            pasajero.setCorreo(pasajeroRequest.getCorreo());
            pasajero.setCelular(pasajeroRequest.getCelular());
            pasajero.setNumDocumento(pasajeroRequest.getNum_documento());
            pasajero.setIdNacionalidad(obtenerEntidadPorId(iNacionalidad, pasajeroRequest.getId_nacionalidad(), "Nacionalidad"));
            iPasajero.save(pasajero);
        }

        // Eliminar y volver a agregar los costos
        iCostos.deleteAllByIdReserva(reservaExistente);
        if (reservaRequest.getCostos() != null && !reservaRequest.getCostos().isEmpty()) {
            for (ReservaRequest.Costos costoRequest : reservaRequest.getCostos()) {
                CostosTours costo = new CostosTours();
                costo.setIdReserva(reservaExistente);
                costo.setDescripcion(costoRequest.getDescripcion());
                costo.setMonto(costoRequest.getMonto());
                iCostos.save(costo);
            }
        }

        // Actualizar la transacción
        Transacciones transaccionExistente = iTransaccion.findByIdReserva(reservaExistente);
        if (transaccionExistente != null) {
            //ya estoy mandando el estado y el tipo moneda
            if (reservaRequest.getIdEstado() == 1) {
                transaccionExistente.setFechaTransaccion(null);
                transaccionExistente.setMontoPagado(0.0);
                transaccionExistente.setEstadoPago(reservaRequest.getTransaccion().getEstado_pago());
                transaccionExistente.setTipoMoneda(reservaRequest.getTransaccion().getTipo_moneda());
                iTransaccion.save(transaccionExistente);
            } else if (reservaRequest.getIdEstado() == 3) {
                transaccionExistente.setFechaTransaccion(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                transaccionExistente.setMontoPagado(reservaRequest.getCosto_total());
                transaccionExistente.setEstadoPago(reservaRequest.getTransaccion().getEstado_pago());
                transaccionExistente.setTipoMoneda(reservaRequest.getTransaccion().getTipo_moneda());
                iTransaccion.save(transaccionExistente);
            }else if (reservaRequest.getIdEstado() == 5) {
                transaccionExistente.setFechaTransaccion(null);
                transaccionExistente.setMontoPagado(null);
                transaccionExistente.setEstadoPago(reservaRequest.getTransaccion().getEstado_pago());
                transaccionExistente.setTipoMoneda(reservaRequest.getTransaccion().getTipo_moneda());
                iTransaccion.save(transaccionExistente);
            }
        }

        return "Reserva actualizada exitosamente";
    }

    private <T> T obtenerEntidadPorId(JpaRepository<T, Integer> repository, Integer id, String nombreEntidad) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(nombreEntidad + " no encontrado"));
    }

    public Date localDateToDate(LocalDate localDate) {
        if (localDate != null) {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }
}
