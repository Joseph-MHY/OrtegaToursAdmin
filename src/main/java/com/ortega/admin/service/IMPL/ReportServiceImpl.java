package com.ortega.admin.service.IMPL;

import com.ortega.admin.models.DTO.request.ReporteRequest;
import com.ortega.admin.service.IReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    private final String[] headers = {
            "ID",
            "ID de reserva",
            "Fecha de registro",
            "Cliente",
            "N° de documento de identidad",
            "N° de pasajeros",
            "Paquete turístico",
            "Categoría del paquete",
            "Tipo de viaje",
            "Conductor Asignado",
            "Monto Total"
    };

    @Override
    public ByteArrayInputStream exportarDatosAExcel(List<ReporteRequest> dataReporte) throws IOException {
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            setBorders(titleStyle);
            Font titleFont = workbook.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            setBorders(headerStyle);

            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            setBorders(centeredStyle);

            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            String titleText = "REPORTE ORTEGA GONZALES " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            titleCell.setCellValue(titleText);
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

            // Crear la fila del encabezado
            Row headerRow = sheet.createRow(1);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue(headers[0]);
            headerCell1.setCellStyle(headerStyle);

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue(headers[1]);
            headerCell2.setCellStyle(headerStyle);

            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue(headers[2]);
            headerCell3.setCellStyle(headerStyle);

            Cell headerCell4 = headerRow.createCell(3);
            headerCell4.setCellValue(headers[3]);
            headerCell4.setCellStyle(headerStyle);

            Cell headerCell5 = headerRow.createCell(4);
            headerCell5.setCellValue(headers[4]);
            headerCell5.setCellStyle(headerStyle);

            Cell headerCell6 = headerRow.createCell(5);
            headerCell6.setCellValue(headers[5]);
            headerCell6.setCellStyle(headerStyle);

            Cell headerCell7 = headerRow.createCell(6);
            headerCell7.setCellValue(headers[6]);
            headerCell7.setCellStyle(headerStyle);

            Cell headerCell8 = headerRow.createCell(7);
            headerCell8.setCellValue(headers[7]);
            headerCell8.setCellStyle(headerStyle);

            Cell headerCell9 = headerRow.createCell(8);
            headerCell9.setCellValue(headers[8]);
            headerCell9.setCellStyle(headerStyle);

            Cell headerCell10 = headerRow.createCell(9);
            headerCell10.setCellValue(headers[9]);
            headerCell10.setCellStyle(headerStyle);

            Cell headerCell11 = headerRow.createCell(10);
            headerCell11.setCellValue(headers[10]);
            headerCell11.setCellStyle(headerStyle);

            int rowNum = 2;
            int i = 1;
            for (ReporteRequest reporte : dataReporte) {
                Row row = sheet.createRow(rowNum++);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(i++);
                cell0.setCellStyle(centeredStyle);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(reporte.getId_reserva());
                cell1.setCellStyle(centeredStyle);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(reporte.getFecha_registro());
                cell2.setCellStyle(centeredStyle);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(reporte.getCliente());
                cell3.setCellStyle(centeredStyle);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(reporte.getNumdocumento());
                cell4.setCellStyle(centeredStyle);

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(reporte.getNum_pasajeros());
                cell5.setCellStyle(centeredStyle);

                Cell cell6 = row.createCell(6);
                cell6.setCellValue(reporte.getNombre_paquete());
                cell6.setCellStyle(centeredStyle);

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(reporte.getCategoria_paquete());
                cell7.setCellStyle(centeredStyle);

                Cell cell8 = row.createCell(8);
                cell8.setCellValue(reporte.getTipo_viaje());
                cell8.setCellStyle(centeredStyle);

                Cell cell9 = row.createCell(9);
                cell9.setCellValue(reporte.getConductor());
                cell9.setCellStyle(centeredStyle);

                Cell cell10 = row.createCell(10);
                cell10.setCellValue(reporte.getCosto_total().doubleValue());
                cell10.setCellStyle(centeredStyle);
            }

            // Establecer ancho de las columnas
            sheet.setColumnWidth(0, 5 * 256); // ID
            sheet.setColumnWidth(1, 13 * 256); // ID de reserva
            sheet.setColumnWidth(2, 17 * 256); // Fecha de registro
            sheet.setColumnWidth(3, 23 * 256); // Cliente
            sheet.setColumnWidth(4, 27 * 256); // N° de documento
            sheet.setColumnWidth(5, 15 * 256); // N° de pasajeros
            sheet.setColumnWidth(6, 22 * 256); // Nombre de paquete
            sheet.setColumnWidth(7, 20 * 256); // Categoría de paquete
            sheet.setColumnWidth(8, 13 * 256); // Tipo de viaje
            sheet.setColumnWidth(9, 28 * 256); // Conductor
            sheet.setColumnWidth(10, 15 * 256); // Total

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private void setBorders(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}
