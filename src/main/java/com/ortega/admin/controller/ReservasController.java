package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.response.NacionalidadResponse;
import com.ortega.admin.models.DTO.response.PaqueteResponse;
import com.ortega.admin.models.DTO.response.ReservaListResponse;
import com.ortega.admin.models.DTO.response.ReservaResponse;
import com.ortega.admin.models.entity.Nacionalidades;
import com.ortega.admin.service.IMPL.ReservaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class ReservasController {

    @Autowired
    private ReservaServiceImpl reservaServiceImpl;

    @GetMapping("/reservas")
    public List<ReservaListResponse> getAllReservas(){
        return reservaServiceImpl.listarReservas();
    }

    @GetMapping("/reserva/obtener/{idReserva}")
    public ReservaResponse getReserva(@PathVariable int idReserva){
        return reservaServiceImpl.obtenerReserva(idReserva);
    }

    @GetMapping("/nacionalidades")
    public List<NacionalidadResponse> getNacionalidades(){
        return reservaServiceImpl.obtenerNacionalidades();
    }

    @GetMapping("/paquetes")
    public PaqueteResponse getPaquetes(){
        return reservaServiceImpl.obtenerPaquetes();
    }
}
