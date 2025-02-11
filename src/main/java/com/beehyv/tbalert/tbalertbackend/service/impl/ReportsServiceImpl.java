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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        try (Workbook workbook = new XSSFWorkbook()) {
            long totalPatients = patientRepo.count();
            int deadPatients = patientRepo.countByCurrentStatus("dead");

            Sheet sheet = createSheetWithHeader(workbook, "Patient Report for Dead", "Category", "Count");
            addDataRow(sheet, 1, "Total Patients", totalPatients);
            addDataRow(sheet, 2, "Total Patients Dead", deadPatients);

            writeWorkbookToFile(workbook, "Patient_ReportDead.xlsx");
            log.info("Report file created for dead patients");
            return 1;
        } catch (Exception e) {
            log.error("Error generating report: {}", e.getMessage());
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
                log.info("No Patient Report found");
                return;
            }

            List<PatientOutputDTO> patientList = patientRegistrationService.getAll();
            if (!patientList.isEmpty()) {
                applyFontAndPopulateSheet(patientList, workbook, sheet);
                writeWorkbookToFile(workbook, filePath);
            }
        } catch (Exception e) {
            log.error("Error in Excel generation: {}", e.getMessage());
            throw new IOException(e);
        }
    }

    @Override
    public void getPatients(Map<String, Object> input) {
        List<PatientOutputDTO> patientList = patientRegistrationService.getFilteredPatients(input);
        log.info(patientList.toString());
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = createSheetWithHeader(workbook, "Patient Report", "Patient ID", "Name", "Year of Birth", "Gender", "Status");
            applyFontAndPopulateSheet(patientList, workbook, sheet);
            writeWorkbookToFile(workbook, "Patient_Report_Filtered.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Sheet createSheetWithHeader(Workbook workbook, String sheetName, String... headers) {
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

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void addDataRow(Sheet sheet, int rowIndex, String category, long count) {
        Row row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(category);
        row.createCell(1).setCellValue(count);
    }

    private void applyFontAndPopulateSheet(List<PatientOutputDTO> patientList, Workbook workbook, Sheet sheet) {
        CellStyle cellStyle = createDataCellStyle(workbook);
        int rowIndex = 1;
        for (PatientOutputDTO patient : patientList) {
            Row row = sheet.createRow(rowIndex++);
            populatePatientRow(row, patient, cellStyle);
        }
    }

    private CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void populatePatientRow(Row row, PatientOutputDTO patient, CellStyle cellStyle) {
        createOrUpdateCell(row, 0, patient.getPatientId(), cellStyle);
        createOrUpdateCell(row, 1, patient.getFirstName() + " " + patient.getLastName(), cellStyle);
        createOrUpdateCell(row, 2, localDateMapper.getYear(patient.getDateOfBirth()), cellStyle);
        createOrUpdateCell(row, 3, patient.getGender(), cellStyle);
        createOrUpdateCell(row, 4, determinePatientStatus(patient), cellStyle);
    }

    private String determinePatientStatus(PatientOutputDTO patient) {
        if (patient.getCurrentStatus() != null) {
            if ("dead".equals(patient.getCurrentStatus())) return "Dead";
            if (patient.isCured()) return "Cured";
        }
        List<PatientFollowUp> followUps = patientFollowUpService.findBeforeDate(patient.getPatientId(), LocalDate.now());
        return followUps.stream().skip(Math.max(followUps.size() - 3, 0)).anyMatch(PatientFollowUp::getOccured) ? "Treatment ongoing" : "No Contact";
    }

    private void createOrUpdateCell(Row row, int columnIndex, Object value, CellStyle cellStyle) {
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

    private void writeWorkbookToFile(Workbook workbook, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            workbook.write(fos);
        }
    }
}
