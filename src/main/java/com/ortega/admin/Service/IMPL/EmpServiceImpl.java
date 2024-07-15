package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.EmpleadoDTO;
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
    public Empleados save(EmpleadoDTO empleadoDTO) {
        // Obtener las entidades necesarias
        Tipodocumento tipoDocumento = tipodocumentoRepository.findById(empleadoDTO.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        Rol rol = rolRepository.findById(empleadoDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Tipocontrato tipoContrato = tipocontratoRepository.findById(empleadoDTO.getIdTipoContrato())
                .orElseThrow(() -> new RuntimeException("Tipo de contrato no encontrado"));

        // Convertir DTO a Entidad
        Empleados empleado = empleadoDTO.toEntity(tipoDocumento, rol, tipoContrato, passwordEncoder.encode(empleadoDTO.getPassword()));

        // Guardar entidad en el repositorio
        Empleados empleadoGuardado = empleadoRepository.save(empleado);

        return empleadoGuardado;
    }
}
