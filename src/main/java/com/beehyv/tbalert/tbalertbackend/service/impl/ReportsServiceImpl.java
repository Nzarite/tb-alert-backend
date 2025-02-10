package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import com.beehyv.tbalert.tbalertbackend.service.ReportsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final PatientRepo patientRepo;
    private final PatientRegistrationService patientRegistrationService;
    private final LocalDateMapper localDateMapper;
    private final PatientFollowUpService patientFollowUpService;

    @Override
    public Integer getAllDead() throws IOException {
        try(Workbook workbook=new XSSFWorkbook()) {
            long totalPatients=patientRepo.count();
            int deadPatients = patientRepo.countByCurrentStatus("dead");

            Sheet sheet = workbook.createSheet("Patient Report for Dead");

            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 3000);

            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont headerFont = (XSSFFont) workbook.createFont();
            headerFont.setFontName("Arial");
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            Row headerRow = sheet.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Category");
            headerCell1.setCellStyle(headerStyle);

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Count");
            headerCell2.setCellStyle(headerStyle);

            Row dataRow = sheet.createRow(1);
            Cell dataCell1 = dataRow.createCell(0);
            dataCell1.setCellValue("Total Patients");
            dataCell1.setCellStyle(dataStyle);

            Cell dataCell2 = dataRow.createCell(1);
            dataCell2.setCellValue(totalPatients);
            dataCell2.setCellStyle(dataStyle);

            dataRow = sheet.createRow(2);
            dataCell1 = dataRow.createCell(0);
            dataCell1.setCellValue("Total Patients Dead");
            dataCell1.setCellStyle(dataStyle);

            dataCell2 = dataRow.createCell(1);
            dataCell2.setCellValue(deadPatients);
            dataCell2.setCellStyle(dataStyle);

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "Patient_ReportDead.xlsx";

            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            log.info("Report file created for dead patients");
            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public void getAllPatients() throws IOException {
        log.info("getAllPatients Excel Generation called");
        String filePath = "Patient_Report.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet("Patient Report");
            if (sheet == null) {
                log.info("No Patient Report");
                return;
            }

            List<PatientOutputDTO> patientList = patientRegistrationService.getAll();
            if (patientList.isEmpty()) return;

            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 14);
            font.setBold(false);
            font.setColor(IndexedColors.LIGHT_BLUE.getIndex());

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setWrapText(true);
            cellStyle.setFont(font);
            populatePatientSheet(sheet, patientList,cellStyle);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            log.error("Error in Excel generation: {}", e.getMessage());
            throw new IOException(e);
        }
    }

    private void populatePatientSheet(Sheet sheet, List<PatientOutputDTO> patientList,CellStyle cellStyle) {
        int rowIndex = 1;
        for (PatientOutputDTO patient : patientList) {
            Row row = getOrCreateRow(sheet, rowIndex++);
            populatePatientRow(row, patient,cellStyle);
        }
    }

    private void populatePatientRow(Row row, PatientOutputDTO patient,CellStyle cellStyle) {
        createOrUpdateCell(row, 0, patient.getPatientId(),cellStyle);
        createOrUpdateCell(row, 1, patient.getFirstName() + " " + patient.getLastName(),cellStyle);
        createOrUpdateCell(row, 2, localDateMapper.getYear(patient.getDateOfBirth()),cellStyle);
        createOrUpdateCell(row, 3, patient.getGender(),cellStyle);
        createOrUpdateCell(row, 4, determinePatientStatus(patient),cellStyle);
    }

    private String determinePatientStatus(PatientOutputDTO patient) {
        if (patient.getCurrentStatus() != null) {
            if ("dead".equals(patient.getCurrentStatus())) return "Dead";
            if (patient.isCured()) return "Cured";
        }

        List<PatientFollowUp> followUps = patientFollowUpService.findBeforeDate(patient.getPatientId(), LocalDate.now());
        return followUps.stream()
                .skip(Math.max(followUps.size() - 3, 0))
                .anyMatch(PatientFollowUp::getOccured) ? "Treatment ongoing" : "No Contact";
    }

    private Row getOrCreateRow(Sheet sheet, int index) {
        return sheet.getRow(index) != null ? sheet.getRow(index) : sheet.createRow(index);
    }

    private void createOrUpdateCell(Row row, int columnIndex, Object value,CellStyle cellStyle) {
        Cell cell = row.getCell(columnIndex) != null ? row.getCell(columnIndex) : row.createCell(columnIndex);
        if (value.getClass().equals(String.class)) {
            cell.setCellValue((String) value);
        } else if (value.getClass().equals(Integer.class)) {
            cell.setCellValue((Integer) value);
        }
        else if (value.getClass().equals(Long.class)) {
            cell.setCellValue((Long) value);
        }
        cell.setCellStyle(cellStyle);
    }


}
