package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.request.ReservaRequest;
import com.ortega.admin.models.DTO.response.NacionalidadResponse;
import com.ortega.admin.models.DTO.response.PaqueteResponse;
import com.ortega.admin.models.DTO.response.ReservaListResponse;
import com.ortega.admin.models.DTO.response.ReservaResponse;
import com.ortega.admin.models.entity.Nacionalidades;
import com.ortega.admin.service.IMPL.ReservaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/reserva/registrar")
    public ResponseEntity<String> registrarReserva(@RequestBody ReservaRequest reservaRequest) {
        try {
            String mensaje = reservaServiceImpl.registrarReserva(reservaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la reserva: " + e.getMessage());
        }
    }

    @PutMapping("/reserva/actualizar/{idReserva}")
    public ResponseEntity<String> actualizarReserva(@PathVariable int idReserva, @RequestBody ReservaRequest reservaRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaServiceImpl.actualizarReserva(idReserva,reservaRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la reserva: " + e.getMessage());
        }
    }
}
