package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.EmpleadoRequest;
import com.ortega.admin.models.DTO.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.models.entity.Rol;
import com.ortega.admin.models.entity.Tipocontrato;
import com.ortega.admin.models.entity.Tipodocumento;
import com.ortega.admin.repositories.IEmpleado;
import com.ortega.admin.repositories.IRol;
import com.ortega.admin.repositories.ITipoContrato;
import com.ortega.admin.repositories.ITipoDocumento;
import com.ortega.admin.service.EmpService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private IRol IRol;
    @Autowired
    private ITipoContrato ITipoContrato;
    @Autowired
    private ITipoDocumento ITipoDocumento;
    @Autowired
    private IEmpleado empleadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Empleados save(EmpleadoRequest empleadoRequest) {
        // Obtener las entidades necesarias
        Tipodocumento tipoDocumento = ITipoDocumento.findById(empleadoRequest.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        Rol rol = IRol.findById(empleadoRequest.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Tipocontrato tipoContrato = ITipoContrato.findById(empleadoRequest.getIdTipoContrato())
                .orElseThrow(() -> new RuntimeException("Tipo de contrato no encontrado"));

        // Convertir DTO a Entidad
        Empleados empleado = empleadoRequest.toEntity(tipoDocumento, rol, tipoContrato, passwordEncoder.encode(empleadoRequest.getPassword()));

        // Guardar entidad en el repositorio
        empleadoRepository.save(empleado);

        return empleado;
    }

    @Override
    public List<EmpleadoResponse> obtenerTodosLosEmpleados() {
        List<Empleados> empleados = empleadoRepository.findAll();
        return empleados.stream().map(this::convertirAEmpleadoResponse).collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponse.EmpleadoUnitResponse obtenerEmpleadoPorId(Integer id) {
        return convetirEmpleado(empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado")));
    }

    @Transactional
    @Override
    public String updateEmpleado(Integer id, EmpleadoRequest.EmpleadoUpdateRequest empleadoUpdateDTO) {
        Empleados empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Tipodocumento tipoDocumento = ITipoDocumento.findById(empleadoUpdateDTO.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        Rol rol = IRol.findById(empleadoUpdateDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Tipocontrato tipoContrato = ITipoContrato.findById(empleadoUpdateDTO.getIdTipoContrato())
                .orElseThrow(() -> new RuntimeException("Tipo de contrato no encontrado"));

        empleado.setNombreApellidos(empleadoUpdateDTO.getNombreApellidos());
        empleado.setDireccion(empleadoUpdateDTO.getDireccion());
        // Conversión de idTipoDocumento, idRol, idTipoContrato a entidades se debe manejar
        empleado.setIdTipoDocumento(tipoDocumento);
        empleado.setNumDocumento(empleadoUpdateDTO.getNumDocumento());
        empleado.setCorreo(empleadoUpdateDTO.getCorreo());
        // La contraseña no se actualiza aquí
        empleado.setFechaNac(Date.from(empleadoUpdateDTO.getFechaNac().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        empleado.setFechaContratacion(Date.from(empleadoUpdateDTO.getFechaContratacion().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        empleado.setTelefono(empleadoUpdateDTO.getTelefono());
        empleado.setContactoEmergencia(empleadoUpdateDTO.getContactoEmergencia());
        empleado.setIdRol(rol);
        empleado.setIdTipoContrato(tipoContrato);
        empleado.setHorarioTrabajo(empleadoUpdateDTO.getHorarioTrabajo());
        empleado.setCuentaBancaria(empleadoUpdateDTO.getCuentaBancaria());
        empleado.setSalario(empleadoUpdateDTO.getSalario());
        empleado.setObservaciones(empleadoUpdateDTO.getObservaciones());
        empleado.setEstadoCuenta(empleadoUpdateDTO.getEstadoCuenta());
        empleadoRepository.save(empleado);
        return "Usuario actualizado exitosamente";
    }

    private EmpleadoResponse convertirAEmpleadoResponse(Empleados empleado) {
        EmpleadoResponse response = new EmpleadoResponse();
        response.setId(empleado.getId());
        response.setNombreApellidos(empleado.getNombreApellidos());
        response.setNumDocumento(empleado.getNumDocumento());
        if (empleado.getIdRol().getNombreRol().equals("ROLE_ADMIN")){
            response.setPuesto("Administrador");
        } else if (empleado.getIdRol().getNombreRol().equals("ROLE_ATTENTION")){
            response.setPuesto("Atención al cliente");
        } else if (empleado.getIdRol().getNombreRol().equals("ROLE_CONDUCTOR")){
            response.setPuesto("Conductor");
        }
        response.setTelefono(empleado.getTelefono());
        response.setSalario(empleado.getSalario());
        response.setEstado(empleado.getEstadoCuenta() ? "Activo" : "Inactivo");
        return response;
    }

    private EmpleadoResponse.EmpleadoUnitResponse convetirEmpleado(Empleados empleado) {
        return EmpleadoResponse.EmpleadoUnitResponse.convertToDTO(empleado);
    }
}
