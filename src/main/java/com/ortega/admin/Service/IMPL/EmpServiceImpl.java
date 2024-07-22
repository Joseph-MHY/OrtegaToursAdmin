package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.EmpleadoRequest;
import com.ortega.admin.models.DTO.EmpleadoResponse;
import com.ortega.admin.models.entity.Empleados;
import com.ortega.admin.models.entity.Rol;
import com.ortega.admin.models.entity.Tipocontrato;
import com.ortega.admin.models.entity.Tipodocumento;
import com.ortega.admin.repositories.EmpleadoRepository;
import com.ortega.admin.repositories.RolRepository;
import com.ortega.admin.repositories.TipocontratoRepository;
import com.ortega.admin.repositories.TipodocumentoRepository;
import com.ortega.admin.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private TipocontratoRepository tipocontratoRepository;
    @Autowired
    private TipodocumentoRepository tipodocumentoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Empleados save(EmpleadoRequest empleadoRequest) {
        // Obtener las entidades necesarias
        Tipodocumento tipoDocumento = tipodocumentoRepository.findById(empleadoRequest.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        Rol rol = rolRepository.findById(empleadoRequest.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Tipocontrato tipoContrato = tipocontratoRepository.findById(empleadoRequest.getIdTipoContrato())
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
