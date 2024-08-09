package com.ortega.admin.service.IMPL;

import com.ortega.admin.Utils.RolEnum;
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
import com.ortega.admin.service.EmpService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpServiceImpl implements EmpService {

    private final ModelMapper modelMapper;
    private final IEmpleado iEmpleado;
    private final PasswordEncoder passwordEncoder;
    private final IRol iRol;
    private final ITipoContrato iTipoContrato;
    private final ITipoDocumento iTipoDocumento;

    @Autowired
    public EmpServiceImpl(ModelMapper modelMapper, IEmpleado iEmpleado, PasswordEncoder passwordEncoder,
                          IRol iRol, ITipoContrato iTipoContrato, ITipoDocumento iTipoDocumento) {
        this.modelMapper = modelMapper;
        this.iEmpleado = iEmpleado;
        this.passwordEncoder = passwordEncoder;
        this.iRol = iRol;
        this.iTipoContrato = iTipoContrato;
        this.iTipoDocumento = iTipoDocumento;
    }

    @Transactional
    @Override
    public String save(EmpleadoRequest empleadoRequest) {
        Empleados emp = iEmpleado.findByCorreo(empleadoRequest.getCorreo());
        System.out.println("Empleado encontrado: " + emp);

        if(emp == null) {
            Tipodocumento tipoDocumento = obtenerEntidadPorId(iTipoDocumento, empleadoRequest.getIdTipoDocumento(), "Tipo de documento");
            Rol rol = obtenerEntidadPorId(iRol, empleadoRequest.getIdRol(), "Rol");
            Tipocontrato tipoContrato = obtenerEntidadPorId(iTipoContrato, empleadoRequest.getIdTipoContrato(), "Tipo de contrato");
            Empleados empleado = empleadoRequest.toEntity(tipoDocumento, rol, tipoContrato, passwordEncoder.encode(empleadoRequest.getPassword()));
            iEmpleado.save(empleado);
            return "Empleado registrado exitosamente";
        } else {
            return "Empleado ya existente";
        }
    }

    @Override
    public List<EmpleadoResponse> obtenerTodosLosEmpleados() {
        List<Empleados> empleados = iEmpleado.findAll();
        return empleados.stream().map(this::convertirAEmpleadoResponse).collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponse.EmpleadoUnitResponse obtenerEmpleadoPorId(Integer id) {
        // Obtener la entidad del empleado
        Empleados empleado = obtenerEntidadPorId(iEmpleado, id, "Empleados");

        // Crear y configurar el objeto EmpleadoUnitResponse
        EmpleadoResponse.EmpleadoUnitResponse response = new EmpleadoResponse.EmpleadoUnitResponse();

        response.setNombreApellidos(empleado.getNombreApellidos().trim());
        response.setDireccion(empleado.getDireccion().trim());
        response.setIdTipoDocumento(empleado.getIdTipoDocumento().getId());
        response.setNumDocumento(empleado.getNumDocumento().trim());
        response.setCorreo(empleado.getCorreo().trim());
        response.setFechaNac(empleado.getFechaNac());
        response.setFechaContratacion(empleado.getFechaContratacion());
        response.setTelefono(empleado.getTelefono().trim());
        response.setContactoEmergencia(empleado.getContactoEmergencia() != null ? empleado.getContactoEmergencia().trim() : null);
        response.setIdRol(empleado.getIdRol().getId());
        response.setIdTipoContrato(empleado.getIdTipoContrato().getId());
        response.setHorarioTrabajo(empleado.getHorarioTrabajo().trim());
        response.setCuentaBancaria(empleado.getCuentaBancaria() != null ? empleado.getCuentaBancaria().trim() : null);
        response.setSalario(empleado.getSalario());
        response.setObservaciones(empleado.getObservaciones() != null ? empleado.getObservaciones().trim() : null);
        response.setEstadoCuenta(empleado.getEstadoCuenta());

        return response;
    }


    @Transactional
    @Override
    public String updateEmpleado(Integer id, EmpleadoRequest.EmpleadoUpdateRequest empleadoUpdateDTO) {
        // Obtener la entidad existente
        Empleados empleado = obtenerEntidadPorId(iEmpleado, id, "Empleados");

        // Actualizar los campos de la entidad manualmente
        empleado.setNombreApellidos(empleadoUpdateDTO.getNombreApellidos().trim());
        empleado.setDireccion(empleadoUpdateDTO.getDireccion().trim());
        empleado.setIdTipoDocumento(obtenerEntidadPorId(iTipoDocumento, empleadoUpdateDTO.getIdTipoDocumento(), "Tipo de documento"));
        empleado.setNumDocumento(empleadoUpdateDTO.getNumDocumento().trim());
        empleado.setCorreo(empleadoUpdateDTO.getCorreo().trim());
        empleado.setFechaNac(java.sql.Date.valueOf(empleadoUpdateDTO.getFechaNac()));
        empleado.setFechaContratacion(java.sql.Date.valueOf(empleadoUpdateDTO.getFechaContratacion()));
        empleado.setTelefono(empleadoUpdateDTO.getTelefono().trim());
        empleado.setContactoEmergencia(empleadoUpdateDTO.getContactoEmergencia() != null ? empleadoUpdateDTO.getContactoEmergencia().trim() : null);
        empleado.setIdRol(obtenerEntidadPorId(iRol, empleadoUpdateDTO.getIdRol(), "Rol"));
        empleado.setIdTipoContrato(obtenerEntidadPorId(iTipoContrato, empleadoUpdateDTO.getIdTipoContrato(), "Tipo de contrato"));
        empleado.setHorarioTrabajo(empleadoUpdateDTO.getHorarioTrabajo().trim());
        empleado.setCuentaBancaria(empleadoUpdateDTO.getCuentaBancaria() != null ? empleadoUpdateDTO.getCuentaBancaria().trim() : null);
        empleado.setSalario(empleadoUpdateDTO.getSalario());
        empleado.setObservaciones(empleadoUpdateDTO.getObservaciones() != null ? empleado.getObservaciones().trim() : null);
        empleado.setEstadoCuenta(empleadoUpdateDTO.getEstadoCuenta());

        // Guardar la entidad actualizada
        iEmpleado.save(empleado);

        return "Usuario actualizado exitosamente";
    }


    @Override
    public List<ConductoresResponse> obtenerTodosLosConductores() {
        List<Object[]> results = iEmpleado.getEmpleadosConductores();
        List<ConductoresResponse> conductores = new ArrayList<>();

        for (Object[] result : results) {
            ConductoresResponse conductor = new ConductoresResponse();
            conductor.setId((Integer) result[0]);
            conductor.setNombre_apellidos((String) result[1]);
            conductores.add(conductor);
        }
        return conductores;
    }

    private EmpleadoResponse convertirAEmpleadoResponse(Empleados empleado) {
        EmpleadoResponse response = modelMapper.map(empleado, EmpleadoResponse.class);
        RolEnum rolEnum = RolEnum.fromNombreRol(empleado.getIdRol().getNombreRol());
        response.setPuesto(rolEnum.getDescripcion());
        response.setEstado(empleado.getEstadoCuenta() ? "Activo" : "Inactivo");
        return response;
    }

    private <T> T obtenerEntidadPorId(JpaRepository<T, Integer> repository, Integer id, String nombreEntidad) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(nombreEntidad + " no encontrado"));
    }
}
