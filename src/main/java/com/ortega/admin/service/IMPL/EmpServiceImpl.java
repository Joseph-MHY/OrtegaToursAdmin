package com.ortega.admin.service.IMPL;

import com.ortega.admin.Utils.RolEnum;
import com.ortega.admin.models.DTO.request.EmpleadoRequest;
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
        Tipodocumento tipoDocumento = obtenerEntidadPorId(iTipoDocumento, empleadoRequest.getIdTipoDocumento(), "Tipo de documento");
        Rol rol = obtenerEntidadPorId(iRol, empleadoRequest.getIdRol(), "Rol");
        Tipocontrato tipoContrato = obtenerEntidadPorId(iTipoContrato, empleadoRequest.getIdTipoContrato(), "Tipo de contrato");
        Empleados empleado = empleadoRequest.toEntity(tipoDocumento, rol, tipoContrato, passwordEncoder.encode(empleadoRequest.getPassword()));
        iEmpleado.save(empleado);
        return "Empleado registrado exitosamente";
    }

    @Override
    public List<EmpleadoResponse> obtenerTodosLosEmpleados() {
        List<Empleados> empleados = iEmpleado.findAll();
        return empleados.stream().map(this::convertirAEmpleadoResponse).collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponse.EmpleadoUnitResponse obtenerEmpleadoPorId(Integer id) {
        return modelMapper.map(obtenerEntidadPorId(iEmpleado, id, "Empleados"), EmpleadoResponse.EmpleadoUnitResponse.class);
    }

    @Transactional
    @Override
    public String updateEmpleado(Integer id, EmpleadoRequest.EmpleadoUpdateRequest empleadoUpdateDTO) {
        Empleados empleado = obtenerEntidadPorId(iEmpleado, id, "Empleados");
        modelMapper.map(empleadoUpdateDTO, empleado);
        iEmpleado.save(empleado);

        return "Usuario actualizado exitosamente";
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
