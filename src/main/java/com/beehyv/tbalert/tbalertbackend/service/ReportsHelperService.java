package com.beehyv.tbalert.tbalertbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class ReportsHelperService {

    public CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public Sheet createSheetWithHeader(Workbook workbook, String sheetName, String... headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5000);
        }
        return sheet;
    }

    public CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public void addDataRow(Sheet sheet, int rowIndex, String category, long count) {
        Row row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(category);
        row.createCell(1).setCellValue(count);
    }

    public void createOrUpdateCell(Row row, int columnIndex, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIndex);
        if (value.getClass().equals(String.class)) {
            cell.setCellValue((String) value);
        } else if (value.getClass().equals(Integer.class)) {
            cell.setCellValue((Integer) value);
        } else if (value.getClass().equals(Long.class)) {
            cell.setCellValue((Long) value);
        }
        cell.setCellStyle(cellStyle);
    }

    public void writeWorkbookToFile(Workbook workbook, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            workbook.write(fos);
        }
    }
}
