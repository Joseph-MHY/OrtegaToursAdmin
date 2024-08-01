package com.ortega.admin.controller;

import com.ortega.admin.models.DTO.request.ReporteRequest;
import com.ortega.admin.models.DTO.response.ReporteResponse;
import com.ortega.admin.service.IMPL.ReportServiceImpl;
import com.ortega.admin.service.IMPL.ReservaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportsController {

    @Autowired
    private ReservaServiceImpl reservaServiceImpl;

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @GetMapping("/reservas")
    public List<ReporteResponse> listarReservas(){
        return reservaServiceImpl.listarReservasParaReporte();
    }

    @PostMapping("/exportar")
    public ResponseEntity<byte[]> exportarReporteAExcel(@RequestBody  List<ReporteRequest> requestList) throws IOException {
        ByteArrayInputStream in = reportServiceImpl.exportarDatosAExcel(requestList);
        UUID uuid = UUID.randomUUID();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte_"+ uuid +".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }
}
