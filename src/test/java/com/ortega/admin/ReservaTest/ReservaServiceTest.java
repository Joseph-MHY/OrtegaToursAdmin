package com.ortega.admin.ReservaTest;

import com.ortega.admin.Utils.TipoViaje;
import com.ortega.admin.models.DTO.request.ReservaRequest;
import com.ortega.admin.models.DTO.response.NacionalidadResponse;
import com.ortega.admin.models.DTO.response.ReporteResponse;
import com.ortega.admin.models.DTO.response.ReservaListResponse;
import com.ortega.admin.models.DTO.response.ReservaResponse;
import com.ortega.admin.models.entity.*;
import com.ortega.admin.repositories.*;
import com.ortega.admin.service.IMPL.ReservaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ReservaServiceTest {
    @Mock
    private IReserva iReserva;
    @Mock
    private INacionalidad iNacionalidad;
    @Mock
    private IPaquete iPaquete;
    @Mock
    private ICategoriaPaquete iCategoriaPaquete;
    @Mock
    private ICliente iCliente;
    @Mock
    private IPasajero iPasajero;
    @Mock
    private ICostos iCostos;
    @Mock
    private ITransaccion iTransaccion;
    @Mock
    private IEstado iEstado;
    @Mock
    private IEmpleado iEmpleado;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarReservasParaReporte() {
        List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1, new Date(), "Cliente 1", "123456", 3, "Paquete A", "Categoria A", "Viaje 1", "Conductor A", BigDecimal.valueOf(100)});
        when(iReserva.findAllReservasForReport()).thenReturn(mockData);

        List<ReporteResponse> result = reservaService.listarReservasParaReporte();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cliente 1", result.get(0).getCliente());
        verify(iReserva, times(1)).findAllReservasForReport();
    }

    @Test
    public void testListarReservas() {
        List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1, "Cliente 1", "123456", "987654321", "Paquete A", "Viaje 1", "Estado A", new Date()});
        when(iReserva.findReservas()).thenReturn(mockData);

        List<ReservaListResponse> result = reservaService.listarReservas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cliente 1", result.get(0).getCliente());
        verify(iReserva, times(1)).findReservas();
    }

    @Test
    public void testObtenerReserva() {
        int idReserva = 1;
        Object[] mockClientData = new Object[]{"Nombre", "Apellido", "123456", "correo@dominio.com", "987654321"};
        Object[] mockTransactionData = new Object[]{1, new Date(), BigDecimal.valueOf(100), "Pagado", "USD"};
        Object[] mockReservaDetails = new Object[]{new Date(), new Date(), 1, "Viaje", 1, BigDecimal.valueOf(50),
                BigDecimal.valueOf(100), BigDecimal.valueOf(150), 1, "Estado", "Notas", "USD"};
        List<Object[]> mockPassengers = Collections.singletonList(new Object[]{"Pasajero", "Apellido", "correo@dominio.com",
                "987654321", 1, "Nacionalidad", "123456"});
        List<Object[]> mockCostos = Collections.singletonList(new Object[]{"Descripcion", BigDecimal.valueOf(50)});
        when(iReserva.getClientById(idReserva)).thenReturn(new Object[]{mockClientData});
        when(iReserva.getTransaccion(idReserva)).thenReturn(new Object[]{mockTransactionData});
        when(iReserva.getReservaDetails(idReserva)).thenReturn(new Object[]{mockReservaDetails});
        when(iReserva.getPassengersByReservation(idReserva)).thenReturn(mockPassengers);
        when(iReserva.getCostosAdicionales(idReserva)).thenReturn(mockCostos);
        ReservaResponse result = reservaService.obtenerReserva(idReserva);
        assertNotNull(result);
        assertEquals("Nombre", result.getCliente().getNombres());
        assertEquals("Apellido", result.getCliente().getApellidos());
        assertEquals("Pagado", result.getTransaccion().getEstado_pago());
        assertEquals(1, result.getPasajeros().size());
        verify(iReserva, times(1)).getClientById(idReserva);
        System.out.println(result);
    }
    @Test
    public void testRegistrarReserva() {
        ReservaRequest reservaRequest = new ReservaRequest();
        ReservaRequest.Cliente clienteRequest = new ReservaRequest.Cliente();
        ReservaRequest.Transaccion transaccionRequest = new ReservaRequest.Transaccion();
        transaccionRequest.setEstado_pago("pendiente");
        clienteRequest.setDocument("12345678");
        reservaRequest.setCliente(clienteRequest);
        reservaRequest.setTransaccion(transaccionRequest);
        reservaRequest.setTipo_viaje(TipoViaje.PUBLICO);

        Clientes cliente = new Clientes();
        cliente.setNumdocumento("12345678");

        when(iCliente.findByNumdocumento("12345678")).thenReturn(null);
        when(iCliente.save(any(Clientes.class))).thenReturn(cliente);
        // Mock del estado
        Estado estadoMock = new Estado();
        estadoMock.setId(1);
        estadoMock.setNombreEstado("Reservado");

        Paquetes paquetesMock = new Paquetes();
        paquetesMock.setId(1);
        paquetesMock.setNombrePaquete("Paquete A");

        Empleados empleadosMock = new Empleados();
        empleadosMock.setId(1);
        empleadosMock.setNombreApellidos("juan perez");
        empleadosMock.setNumDocumento("84758473");

        reservaRequest.setIdEstado(estadoMock.getId());
        reservaRequest.setIdPaquete(paquetesMock.getId());
        reservaRequest.setIdEmpleado(empleadosMock.getId());

        when(iEstado.findById(anyInt())).thenReturn(Optional.of(estadoMock));
        when(iPaquete.findById(anyInt())).thenReturn(Optional.of(paquetesMock));
        when(iEmpleado.findById(anyInt())).thenReturn(Optional.of(empleadosMock));

        // Mock del guardado de la reserva
        Reservas reservaMock = new Reservas();
        when(iReserva.save(any(Reservas.class))).thenReturn(reservaMock);

        // Llamada al método registrarReserva
        String resultado = reservaService.registrarReserva(reservaRequest);

        verify(iCliente, times(1)).save(any(Clientes.class));
        verify(iReserva, times(1)).save(any(Reservas.class));
        verify(iEstado, times(1)).findById(anyInt());
        assertEquals("Reserva registrada exitosamente", resultado);
    }
}
