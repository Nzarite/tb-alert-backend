package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ReportsHelperService {

    private final LocalDateMapper localDateMapper;

    public CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        BorderStyle border = BorderStyle.THIN;
        style.setBorderTop(border);
        style.setBorderBottom(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public Sheet createSheetWithHeader(int columnWidth,Workbook workbook, String sheetName, String... headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, columnWidth);
        }
        return sheet;
    }

    public CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        BorderStyle border = BorderStyle.THIN;
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        style.setBorderTop(border);
        style.setBorderBottom(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
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
        if(value!=null) {
            if (value.getClass().equals(String.class) && !value.toString().isEmpty()) {
                cell.setCellValue((String) value);
            } else if (value.getClass().equals(Integer.class)) {
                cell.setCellValue((Integer) value);
            } else if (value.getClass().equals(Long.class)) {
                cell.setCellValue((Long) value);
            }
            else if (value.getClass().equals(Boolean.class)) {
                cell.setCellValue((Boolean) value);
            }
            else if (value.getClass().equals(LocalDate.class)) {
                cell.setCellValue(localDateMapper.toDate((LocalDate) value));
            }
            else if (value.getClass().equals(LocalDateTime.class)) {
                cell.setCellValue(localDateMapper.toDateTime((LocalDateTime) value));
            }
        }
        else cell.setCellValue("NULL");
        cell.setCellStyle(cellStyle);
    }

    public void writeWorkbookToFile(Workbook workbook, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            workbook.write(fos);
        }
    }
}
