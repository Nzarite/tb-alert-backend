package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import com.beehyv.tbalert.tbalertbackend.service.ReportsHelperService;
import com.beehyv.tbalert.tbalertbackend.service.ReportsService;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final PatientRepo patientRepo;
    private final PatientRegistrationService patientRegistrationService;
    private final LocalDateMapper localDateMapper;
    private final ReportsHelperService reportsHelperService;
    private final TeleCallerService teleCallerService;


    @Override
    public Integer getAllDead() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            long totalPatients = patientRepo.count();
            int deadPatients = patientRepo.countByCurrentStatus("dead");

            Sheet sheet = reportsHelperService.createSheetWithHeader(workbook, "Patient Report for Dead", "Category", "Count");
            reportsHelperService.addDataRow(sheet, 1, "Total Patients", totalPatients);
            reportsHelperService.addDataRow(sheet, 2, "Total Patients Dead", deadPatients);

            reportsHelperService.writeWorkbookToFile(workbook, "Patient_ReportDead.xlsx");
            log.info("Report file created for dead patients");
            return 1;
        } catch (Exception e) {
            log.error("Error generating report: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public byte[] getPatients(Map<String, Object> input) {
        List<PatientOutputDTO> patientList = patientRegistrationService.getFilteredPatients(input);
        log.info(patientList.toString());
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = reportsHelperService.createSheetWithHeader(workbook, "Patient Report", "Patient ID", "Name", "Year of Birth", "Gender", "Status");
            applyFontAndPopulateSheet(patientList, workbook, sheet);
            reportsHelperService.writeWorkbookToFile(workbook, "Patient_Report_Filtered.xlsx");
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error generating report: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void getTeleCallerOfAState(String state) {
        List<TeleCallerOutputDTO>teleCallerOutputDTOList=teleCallerService.getByState(state);

    }


    private void applyFontAndPopulateSheet(List<PatientOutputDTO> patientList, Workbook workbook, Sheet sheet) {
        CellStyle cellStyle = reportsHelperService.createDataCellStyle(workbook);
        int rowIndex = 1;
        for (PatientOutputDTO patient : patientList) {
            Row row = sheet.createRow(rowIndex++);
            populatePatientRow(row, patient, cellStyle);
        }
    }

    private void populatePatientRow(Row row, PatientOutputDTO patient, CellStyle cellStyle) {

        reportsHelperService.createOrUpdateCell(row, 0, patient.getPatientId(), cellStyle);
        reportsHelperService.createOrUpdateCell(row, 1, patient.getFirstName() + " " + patient.getLastName(), cellStyle);
        reportsHelperService.createOrUpdateCell(row, 2, localDateMapper.getYear(patient.getDateOfBirth()), cellStyle);
        reportsHelperService.createOrUpdateCell(row, 3, patient.getGender(), cellStyle);
        reportsHelperService.createOrUpdateCell(row, 4, patientRegistrationService.determinePatientStatus(patient), cellStyle);
    }

}
