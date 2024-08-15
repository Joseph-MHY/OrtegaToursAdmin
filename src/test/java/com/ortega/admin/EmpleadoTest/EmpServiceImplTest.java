package com.ortega.admin.EmpleadoTest;

import com.ortega.admin.models.DTO.request.EmpleadoRequest;
import com.ortega.admin.models.DTO.response.ConductoresResponse;
import com.ortega.admin.models.DTO.response.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.models.entity.Rol;
import com.ortega.admin.models.entity.Tipocontrato;
import com.ortega.admin.models.entity.Tipodocumento;
import com.ortega.admin.repositories.IEmpleado;
import com.ortega.admin.repositories.IRol;
import com.ortega.admin.repositories.ITipoContrato;
import com.ortega.admin.repositories.ITipoDocumento;
import com.ortega.admin.service.IMPL.EmpServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpServiceImplTest {

    @InjectMocks
    private EmpServiceImpl empService;

    @Mock
    private IEmpleado iEmpleado;

    @Mock
    private IRol iRol;

    @Mock
    private ITipoContrato iTipoContrato;

    @Mock
    private ITipoDocumento iTipoDocumento;

    @Mock
    private ModelMapper modelMapper;

    private Empleados empleado1;
    private Empleados empleado2;
    private Tipodocumento tipodocumentoDNI;
    private Tipodocumento tipodocumentoPasaporte;
    private Rol rolAdmin;
    private Rol rolConductor;
    private Tipocontrato contratoTiempoCompleto;
    private Tipocontrato contratoMedioTiempo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock data
        tipodocumentoDNI = new Tipodocumento();
        tipodocumentoDNI.setId(1);
        tipodocumentoDNI.setNombreDocumento("DNI");

        tipodocumentoPasaporte = new Tipodocumento();
        tipodocumentoPasaporte.setId(2);
        tipodocumentoPasaporte.setNombreDocumento("Pasaporte");

        rolAdmin = new Rol();
        rolAdmin.setId(1);
        rolAdmin.setNombreRol("ROLE_ADMIN");

        rolConductor = new Rol();
        rolConductor.setId(2);
        rolConductor.setNombreRol("ROLE_CONDUCTOR");

        contratoTiempoCompleto = new Tipocontrato();
        contratoTiempoCompleto.setId(1);
        contratoTiempoCompleto.setNombreContrato("Tiempo Completo");

        contratoMedioTiempo = new Tipocontrato();
        contratoMedioTiempo.setId(2);
        contratoMedioTiempo.setNombreContrato("Medio Tiempo");

        // Setup mock data for Empleados
        empleado1 = new Empleados();
        empleado1.setId(1);
        empleado1.setNombreApellidos("John Doe");
        empleado1.setDireccion("123 Main St");
        empleado1.setIdTipoDocumento(tipodocumentoDNI);
        empleado1.setNumDocumento("12345678");
        empleado1.setCorreo("john.doe@test.com");
        empleado1.setPassword("password123");
        empleado1.setFechaNac(new Date());
        empleado1.setFechaContratacion(new Date());
        empleado1.setTelefono("123456789");
        empleado1.setContactoEmergencia("987654321");
        empleado1.setIdRol(rolAdmin);
        empleado1.setIdTipoContrato(contratoTiempoCompleto);
        empleado1.setHorarioTrabajo("9 AM - 5 PM");
        empleado1.setCuentaBancaria("1234567890");
        empleado1.setSalario(2500.0);
        empleado1.setObservaciones("Ninguna");
        empleado1.setEstadoCuenta(true);

        empleado2 = new Empleados();
        empleado2.setId(2);
        empleado2.setNombreApellidos("Jane Smith");
        empleado2.setDireccion("456 Elm St");
        empleado2.setIdTipoDocumento(tipodocumentoPasaporte);
        empleado2.setNumDocumento("87654321");
        empleado2.setCorreo("jane.smith@test.com");
        empleado2.setPassword("password321");
        empleado2.setFechaNac(new Date());
        empleado2.setFechaContratacion(new Date());
        empleado2.setTelefono("987654321");
        empleado2.setContactoEmergencia("123456789");
        empleado2.setIdRol(rolConductor);
        empleado2.setIdTipoContrato(contratoMedioTiempo);
        empleado2.setHorarioTrabajo("10 AM - 4 PM");
        empleado2.setCuentaBancaria("0987654321");
        empleado2.setSalario(1500.0);
        empleado2.setObservaciones("Ninguna");
        empleado2.setEstadoCuenta(true);

        // Setup mocks for repositories
        List<Empleados> empleados = new ArrayList<>();
        empleados.add(empleado1);
        empleados.add(empleado2);
        when(iEmpleado.findAll()).thenReturn(empleados);
        when(iEmpleado.findByCorreo(anyString())).thenReturn(null);
        when(iEmpleado.findByNumDocumento(anyString())).thenReturn(null);

        // Mocks for TipoDocumento
        when(iTipoDocumento.findById(1)).thenReturn(Optional.of(tipodocumentoDNI));
        when(iTipoDocumento.findById(2)).thenReturn(Optional.of(tipodocumentoPasaporte));

        // Mocks for Rol
        when(iRol.findById(1)).thenReturn(Optional.of(rolAdmin));
        when(iRol.findById(2)).thenReturn(Optional.of(rolConductor));

        // Mocks for TipoContrato
        when(iTipoContrato.findById(1)).thenReturn(Optional.of(contratoTiempoCompleto));
        when(iTipoContrato.findById(2)).thenReturn(Optional.of(contratoMedioTiempo));
    }

    @Test
    void testSaveSuccess() {
        EmpleadoRequest empleadoRequest = new EmpleadoRequest();
        empleadoRequest.setNombreApellidos("John Doe");
        empleadoRequest.setDireccion("123 Main St");
        empleadoRequest.setCorreo("test@test.com");
        empleadoRequest.setNumDocumento("123456789");
        empleadoRequest.setIdTipoDocumento(1);
        empleadoRequest.setIdRol(1);
        empleadoRequest.setIdTipoContrato(1);
        empleadoRequest.setPassword("password");
        empleadoRequest.setFechaNac(new Date());
        empleadoRequest.setFechaContratacion(new Date());
        empleadoRequest.setTelefono("123456789");
        empleadoRequest.setHorarioTrabajo("9 AM - 5 PM");
        empleadoRequest.setSalario(3000.0);

        when(iEmpleado.findByCorreo(empleadoRequest.getCorreo())).thenReturn(null);
        when(iEmpleado.findByNumDocumento(empleadoRequest.getNumDocumento())).thenReturn(null);

        String result = empService.save(empleadoRequest);
        System.out.println(result);
        assertEquals("Empleado registrado exitosamente", result);
    }

    @Test
    void testSaveCorreoExistente() {
        EmpleadoRequest empleadoRequest = new EmpleadoRequest();
        empleadoRequest.setCorreo("test@test.com");

        when(iEmpleado.findByCorreo(empleadoRequest.getCorreo())).thenReturn(new Empleados());

        String result = empService.save(empleadoRequest);
        System.out.println(result);
        assertEquals("Correo ya existente", result);
    }

    @Test
    void testSaveDocumentoExistente() {
        EmpleadoRequest empleadoRequest = new EmpleadoRequest();
        empleadoRequest.setNumDocumento("123456789");

        when(iEmpleado.findByNumDocumento(empleadoRequest.getNumDocumento())).thenReturn(new Empleados());

        String result = empService.save(empleadoRequest);
        System.out.println(result);
        assertEquals("Documento ya existente", result);
    }

    @Test
    void testUpdateEmpleadoSuccess() {
        Integer id = 1;
        EmpleadoRequest.EmpleadoUpdateRequest updateRequest = new EmpleadoRequest.EmpleadoUpdateRequest();
        updateRequest.setNombreApellidos("Updated Name");
        updateRequest.setDireccion("Updated Address");
        updateRequest.setCorreo("updated@test.com");
        updateRequest.setNumDocumento("987654321");
        updateRequest.setIdTipoDocumento(2);
        updateRequest.setIdRol(2);
        updateRequest.setIdTipoContrato(2);
        updateRequest.setFechaNac(LocalDate.now());
        updateRequest.setFechaContratacion(LocalDate.now());
        updateRequest.setTelefono("987654321");
        updateRequest.setHorarioTrabajo("10 AM - 6 PM");
        updateRequest.setCuentaBancaria("1234567890");
        updateRequest.setSalario(3500.0);
        updateRequest.setObservaciones("Updated Observations");
        updateRequest.setEstadoCuenta(true);

        Empleados existingEmpleado = new Empleados();
        existingEmpleado.setIdRol(rolAdmin); // Ensure Rol is set
        existingEmpleado.setNombreApellidos("Existing Name");
        when(iEmpleado.findById(id)).thenReturn(Optional.of(existingEmpleado));
        when(iEmpleado.save(any(Empleados.class))).thenReturn(existingEmpleado);

        String result = empService.updateEmpleado(id, updateRequest);
        System.out.println(result);
        assertEquals("Usuario actualizado exitosamente", result);
    }

    @Test
    void testUpdateEmpleadoNotFound() {
        Integer id = 1;
        EmpleadoRequest.EmpleadoUpdateRequest updateRequest = new EmpleadoRequest.EmpleadoUpdateRequest();

        when(iEmpleado.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> empService.updateEmpleado(id, updateRequest), "Empleado no actualizado");
    }

    @Test
    void testObtenerTodosLosEmpleados() {
        // Setup mock data as configured in setUp()
        List<Empleados> empleados = new ArrayList<>();
        empleados.add(empleado1);
        empleados.add(empleado2);
        when(iEmpleado.findAll()).thenReturn(empleados);

        // Mockear el mapeo del ModelMapper
        when(modelMapper.map(empleado1, EmpleadoResponse.class)).thenReturn(new EmpleadoResponse(
                1, "John Doe", "12345678", "Admin", "123456789", 2500.0, "Activo"));
        when(modelMapper.map(empleado2, EmpleadoResponse.class)).thenReturn(new EmpleadoResponse(
                2, "Jane Smith", "87654321", "Conductor", "987654321", 1500.0, "Activo"));

        List<EmpleadoResponse> response = empService.obtenerTodosLosEmpleados();
        System.out.println(response);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals("John Doe", response.get(0).getNombreApellidos());
        assertEquals("Jane Smith", response.get(1).getNombreApellidos());
    }

    @Test
    void testObtenerEmpleadoPorIdSuccess() {
        Integer id = 1;
        Empleados empleado = new Empleados();
        empleado.setId(id);
        empleado.setNombreApellidos("John Doe");
        empleado.setDireccion("123 Main St");
        empleado.setIdTipoDocumento(tipodocumentoDNI);
        empleado.setNumDocumento("987654321");
        empleado.setCorreo("john.doe@test.com");
        empleado.setPassword("password123");
        empleado.setFechaNac(new Date());
        empleado.setFechaContratacion(new Date());
        empleado.setTelefono("123456789");
        empleado.setContactoEmergencia("987654321");
        empleado.setIdRol(rolAdmin);
        empleado.setIdTipoContrato(contratoTiempoCompleto);
        empleado.setHorarioTrabajo("9 AM - 5 PM");
        empleado.setCuentaBancaria("1234567890");
        empleado.setSalario(2500.0);
        empleado.setObservaciones("Ninguna");
        empleado.setEstadoCuenta(true);

        EmpleadoResponse.EmpleadoUnitResponse empleadoResponse = new EmpleadoResponse.EmpleadoUnitResponse();
        empleadoResponse.setNombreApellidos("John Doe");
        empleadoResponse.setDireccion("123 Main St");
        empleadoResponse.setIdTipoDocumento(1);
        empleadoResponse.setNumDocumento("987654321");
        empleadoResponse.setCorreo("john.doe@test.com");
        empleadoResponse.setFechaNac(new Date());
        empleadoResponse.setFechaContratacion(new Date());
        empleadoResponse.setTelefono("123456789");
        empleadoResponse.setContactoEmergencia("987654321");
        empleadoResponse.setIdRol(1);
        empleadoResponse.setIdTipoContrato(1);
        empleadoResponse.setHorarioTrabajo("9 AM - 5 PM");
        empleadoResponse.setCuentaBancaria("1234567890");
        empleadoResponse.setSalario(2500.0);
        empleadoResponse.setObservaciones("Ninguna");
        empleadoResponse.setEstadoCuenta(true);

        // Mockear el repositorio y el mapeo
        when(iEmpleado.findById(id)).thenReturn(Optional.of(empleado));
        when(modelMapper.map(empleado, EmpleadoResponse.EmpleadoUnitResponse.class)).thenReturn(empleadoResponse);

        // Ejecutar el método y verificar el resultado
        EmpleadoResponse.EmpleadoUnitResponse response = empService.obtenerEmpleadoPorId(id);
        System.out.println(response);
        assertNotNull(response);
        assertEquals("John Doe", response.getNombreApellidos());
        assertEquals("123 Main St", response.getDireccion());
        assertEquals("987654321", response.getNumDocumento());
        assertEquals("john.doe@test.com", response.getCorreo());
        assertEquals("123456789", response.getTelefono());
        assertEquals("987654321", response.getContactoEmergencia());
        assertEquals(1, response.getIdRol());
        assertEquals(1, response.getIdTipoContrato());
        assertEquals("9 AM - 5 PM", response.getHorarioTrabajo());
        assertEquals("1234567890", response.getCuentaBancaria());
        assertEquals(2500.0, response.getSalario());
        assertEquals("Ninguna", response.getObservaciones());
        assertTrue(response.getEstadoCuenta());
    }

    @Test
    void testObtenerEmpleadoPorIdNotFound() {
        Integer id = 1;
        when(iEmpleado.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> empService.obtenerEmpleadoPorId(id), "Empleado no Encontrado");
    }

    @Test
    void testObtenerTodosLosConductores() {
        // Se instancia un conductor
        Empleados conductor1 = new Empleados();
        conductor1.setId(2);
        conductor1.setNombreApellidos("Jane Smith");
        conductor1.setIdRol(rolConductor);

        // Simula el resultado que getEmpleadosConductores() debería devolver
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{conductor1.getId(), conductor1.getNombreApellidos()});

        // Mockear el repositorio para devolver solo los conductores
        when(iEmpleado.getEmpleadosConductores()).thenReturn(results);

        // Mockear el mapeo del ModelMapper para ConductoresResponse
        ConductoresResponse conductoresResponse = new ConductoresResponse();
        conductoresResponse.setId(conductor1.getId());
        conductoresResponse.setNombre_apellidos(conductor1.getNombreApellidos());

        when(modelMapper.map(conductor1, ConductoresResponse.class)).thenReturn(conductoresResponse);

        // Ejecutar el método
        List<ConductoresResponse> conductores = empService.obtenerTodosLosConductores();

        // Imprimir para verificar el contenido
        System.out.println("Conductores: " + conductores);

        // Verificar resultados
        assertNotNull(conductores, "La lista de conductores no debe ser nula.");
        assertEquals(1, conductores.size(), "El tamaño de la lista de conductores no es correcto.");
        assertEquals("Jane Smith", conductores.get(0).getNombre_apellidos(), "El nombre del conductor no es el esperado.");
    }
}
