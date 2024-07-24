package com.ortega.admin.service;

import com.ortega.admin.models.DTO.ReporteRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface IReportService {

    ByteArrayInputStream exportarDatosAExcel(List<ReporteRequest> dataReporte) throws IOException;
}
