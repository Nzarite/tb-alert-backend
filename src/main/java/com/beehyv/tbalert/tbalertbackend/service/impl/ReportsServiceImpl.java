package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.output.*;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto.FollowUpDetails;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.StateHeadMapper;
import com.beehyv.tbalert.tbalertbackend.repository.*;
import com.beehyv.tbalert.tbalertbackend.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private final ReportsHelperService reportsHelperService;
    private final TeleCallerService teleCallerService;
    private final NikshayMitraRepo nikshayMitraRepo;
    private final ContactScreeningRepo contactScreeningRepos;
    private final TBDetailsRepo tbDetailsRepo;
    private final PatientFollowUpService patientFollowUpService;
    private final PersonMapper personMapper;
    private final StateHeadService stateHeadService;
    private final PersonService personService;
    private final PatientMapper patientMapper;
    private final PatientFollowUpRepo patientFollowUpRepo;
    private final String stateLiteral="State";
    private final String emailLiteral="Email";
    private final StateHeadRepo stateHeadRepo;
    private final StateHeadMapper stateHeadMapper;


    @Override
    public Integer getAllDead() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            long totalPatients = patientRepo.count();
            int deadPatients = patientRepo.countByCurrentStatusAndPerson_IsDeletedFalse("dead");

            Sheet sheet = reportsHelperService.createSheetWithHeader(7000,workbook, "Patient Report for Dead", "Category", "Count");
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
    public byte[] getPatients(Map<String, Object> input) throws IOException {
        List<PatientOutputDTO> patientList = patientRegistrationService.getFilteredPatients(input);
        log.info(patientList.toString());
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = reportsHelperService.createSheetWithHeader(10000,workbook, "Patient Report",
                    "Patient ID", "Name", "Gender", "Age",
                    "Phone Number", emailLiteral, "Block", "GPHead", "Village", "District", stateLiteral, "Current Status",
                    "Cured", "Created At", "Created By", "Updated By", "Nikshay ID", "UDST Status",
                    "Date Of UDST", "UDST Result", "DBT Status", "Date Of DBT", "Nikshay Mitra Status",
                    "Nikshay Mitra Date", "Nikshay Mitra Name", "Contact Screening Done",
                    "Date Of Contact Screening", "No Of HHCs Available", "No Of HHCs Screened",
                    "No Of HHCs With TB Symptoms", "No Of HHCs Referred TB Testing", "No Of HHCs Diagnosed TB",
                    "No Of HHCs TB Initiated ATT", "No Of HHCs Undergone LTBI Test", "No Of Eligible For TPT",
                    "No Of HHCs Initiated TPT", "Date Of Diagnosis", "Date Of Treatment Initiation",
                    "Type Of PWTB", "Type Of TB", "DSTB Or DRTB",
                    "Follow up 1 Date", "Follow up 1 Missed Medication", "Follow up 1 Patient Condition",
                    "Follow up 2 Date", "Follow up 2 Missed Medication", "Follow up 2 Patient Condition",
                    "Follow up 3 Date", "Follow up 3 Missed Medication", "Follow up 3 Patient Condition",
                    "Follow up 4 Date", "Follow up 4 Missed Medication", "Follow up 4 Patient Condition",
                    "Follow up 5 Date", "Follow up 5 Missed Medication", "Follow up 5 Patient Condition",
                    "Follow up 6 Date", "Follow up 6 Missed Medication", "Follow up 6 Patient Condition",
                    "Follow up 7 Date", "Follow up 7 Missed Medication", "Follow up 7 Patient Condition",
                    "Follow up 8 Date", "Follow up 8 Missed Medication", "Follow up 8 Patient Condition",
                    "Patient Is Deleted"
            );
            applyFontAndPopulateSheet(patientList, workbook, sheet);
            reportsHelperService.writeWorkbookToFile(workbook, "Patient_Report_Filtered.xlsx");
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error generating report: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public byte[] getTeleCallerOfAState(Map<String, Object> filter) throws IOException {
        try(Workbook workbook=new XSSFWorkbook();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream())
        {
            String state=filter.get("state").toString();
            String isDeleted=filter.get("isDeleted").toString();
            List<TeleCallerOutputDTO>teleCallerOutputDTOList;
            if(isDeleted.isEmpty())
            {
                if(state.isEmpty())
                    teleCallerOutputDTOList=teleCallerService.getAll();
                else teleCallerOutputDTOList=teleCallerService.getAllByState(state);
            }
            else if(isDeleted.equals("true"))
            {
                if(state.isEmpty())
                    teleCallerOutputDTOList=teleCallerService.getAllDeleted();
                else teleCallerOutputDTOList=teleCallerService.getDeletedByState(state);
            }
            else {
                if(state.isEmpty())
                    teleCallerOutputDTOList=teleCallerService.getAllNotDeleted();
                else teleCallerOutputDTOList=teleCallerService.getByState(state);
            }
            log.info(teleCallerOutputDTOList.toString());
            Sheet sheet=reportsHelperService.createSheetWithHeader(7000,workbook,"Telecaller Details","Id","Name",stateLiteral,emailLiteral,
                    "Phone number","Date Of Joining","Patients Registered","Date of Leaving");
            applyFontAndPopulateSheet(teleCallerOutputDTOList, workbook, sheet);
            reportsHelperService.writeWorkbookToFile(workbook, "TeleCallerDetails.xlsx");
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            log.error("Error generating report: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public byte[] getStateHeads(Map<String, Object> filter) throws IOException {
        try(Workbook workbook=new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream())
        {
            List<StateHeadOutputDTO>stateHeadOutputDTOS;
            String state="";
            if(filter.get("state") != null)
                state=filter.get("state").toString();
            String isDeleted="";
            if(filter.get("isDeleted") != null)
                isDeleted=filter.get("isDeleted").toString();
            if(isDeleted.isEmpty())
            {
                if(state.isEmpty())
                    stateHeadOutputDTOS=stateHeadService.getAll();
                else stateHeadOutputDTOS=stateHeadService.getAllByState(state);
            }
            else if(isDeleted.equals("true"))
            {
                if(state.isEmpty())
                    stateHeadOutputDTOS=stateHeadService.getAllDeleted();
                else stateHeadOutputDTOS=stateHeadService.getAllDeletedByState(state);
            }
            else {
                if(state.isEmpty())
                    stateHeadOutputDTOS=stateHeadService.getAllNotDeleted();
                else stateHeadOutputDTOS=stateHeadService.getAllNotDeletedByState(state);
            }
            Sheet sheet=reportsHelperService.createSheetWithHeader(7000,workbook,"StateHead Details","Id","Name",stateLiteral,emailLiteral,
                    "Phone number","Date Of Joining","TeleCallers Registered","Date of Leaving");
            applyFontAndPopulateSheet(stateHeadOutputDTOS, workbook, sheet);
            reportsHelperService.writeWorkbookToFile(workbook, "StateHeads.xlsx");
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            log.error("Error generating report: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public byte[] getPatientFollowUp(Map<String, Object> filter) throws IOException {

        try(Workbook workbook=new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream())
        {
            List<PatientFollowUpOutputForFrontEndDto>patientFollowUpOutputForFrontEndDtos;
            List<PatientOutputDTO>patients;
            patients=patientRegistrationService.getFilteredPatients(filter);
            patientFollowUpOutputForFrontEndDtos=patientFollowUpService.getFollowUpForPatientList(patients);
            Sheet sheet=reportsHelperService.createSheetWithHeader(9000,workbook,"PatientFollowUp Details",
                    "Id","Name",
                    "FollowUp 1 Date","FollowUp 1 Paitent Condition","FollowUp 1 Medication Name","FollowUp 1 Missed Dosages",
                    "FollowUp 2 Date","FollowUp 2 Paitent Condition","FollowUp 2 Medication Name","FollowUp 2 Missed Dosages",
                    "FollowUp 3 Date","FollowUp 3 Paitent Condition","FollowUp 3 Medication Name","FollowUp 3 Missed Dosages",
                    "FollowUp 4 Date","FollowUp 4 Paitent Condition","FollowUp 4 Medication Name","FollowUp 4 Missed Dosages",
                    "FollowUp 5 Date","FollowUp 5 Paitent Condition","FollowUp 5 Medication Name","FollowUp 5 Missed Dosages",
                    "FollowUp 6 Date","FollowUp 6 Paitent Condition","FollowUp 6 Medication Name","FollowUp 6 Missed Dosages",
                    "FollowUp 7 Date","FollowUp 7 Paitent Condition","FollowUp 7 Medication Name","FollowUp 7 Missed Dosages",
                    "FollowUp 8 Date","FollowUp 8 Paitent Condition","FollowUp 8 Medication Name","FollowUp 8 Missed Dosages"
                    );
            sheet.setColumnWidth(0,sheet.getColumnWidth(0));
            applyFontAndPopulateSheet(patientFollowUpOutputForFrontEndDtos, workbook, sheet);
            reportsHelperService.writeWorkbookToFile(workbook, "PatientFollowUpDetails.xlsx");
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            log.error("Error generating report: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public byte[] getPatientFollowUpForToday(Map<String, Object> filter) throws IOException {
        try(Workbook workbook=new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream())
        {

            List<String>patientIds=patientRegistrationService.getFilteredPatients(filter).stream().map(PatientOutputDTO::getPatientId).toList();
            List<PatientFollowUp>patientFollowUps=patientFollowUpRepo.findAllByDateAndPatient_IdIn(LocalDate.now(),patientIds);

            Sheet sheet=reportsHelperService.createSheetWithHeader(7000,workbook,"Follow Up for Today","Patient Id","Patient Name","Patient Phone Number","Type of TB");
            sheet.setColumnWidth(0,sheet.getColumnWidth(0));
            applyFontAndPopulateSheet(patientFollowUps, workbook, sheet);
            reportsHelperService.writeWorkbookToFile(workbook, "FollowUpsForToday.xlsx");
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            log.error("Error generating report: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }


    private void applyFontAndPopulateSheet(List<?> list, Workbook workbook, Sheet sheet) {
        CellStyle cellStyle = reportsHelperService.createDataCellStyle(workbook);
        int rowIndex = 1;
        for (var object : list) {
            Row row = sheet.createRow(rowIndex++);
            if(object.getClass()== PatientOutputDTO.class)
                populatePatientRow(row, (PatientOutputDTO) object, cellStyle);

            else if(object.getClass()== TeleCallerOutputDTO.class)
                populateTeleCallerRow(row,(TeleCallerOutputDTO) object,cellStyle);

            else if(object.getClass()== StateHeadOutputDTO.class)
                populateStateHeadRow(row,(StateHeadOutputDTO) object,cellStyle);

            else if(object.getClass()== PatientFollowUpOutputForFrontEndDto.class) {
                populatePatientFollowUpRow(row, (PatientFollowUpOutputForFrontEndDto) object, cellStyle);
            }
            else if(object.getClass()== PatientFollowUp.class)
            {
                populateFollowUpForToday(row,(PatientFollowUp)object,cellStyle);
            }
        }
    }

    private void populateFollowUpForToday(Row row, PatientFollowUp patientFollowUp, CellStyle cellStyle) {
        int ind=0;
        reportsHelperService.createOrUpdateCell(row,ind++,patientFollowUp.getPatient().getId(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,ind++,patientFollowUp.getPatient().getPerson().getFirstName()+" "+patientFollowUp.getPatient().getPerson().getLastName(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,ind++,patientFollowUp.getPatient().getPerson().getPhoneNumber(),cellStyle);

        TBDetails tbDetails=tbDetailsRepo.findByPatient_Id(patientFollowUp.getPatient().getId()).orElse(null);
        String typeOfTb="";

        if(tbDetails!=null) typeOfTb=tbDetails.getTypeOfTb();

        reportsHelperService.createOrUpdateCell(row,ind++,typeOfTb,cellStyle);
    }

    private void populatePatientFollowUpRow(Row row, PatientFollowUpOutputForFrontEndDto followUp, CellStyle cellStyle) {
        int ind=0;
        reportsHelperService.createOrUpdateCell(row,ind++,followUp.getPatient().getPatientId(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,ind++,followUp.getPatient().getFirstName()+followUp.getPatient().getLastName(),cellStyle);

        for(FollowUpDetails followUpDetails : followUp.getFollowUpDetails())
        {
            reportsHelperService.createOrUpdateCell(row,ind++,followUpDetails.getDate(),cellStyle);
            reportsHelperService.createOrUpdateCell(row,ind++,followUpDetails.getPatientCondition(),cellStyle);
            reportsHelperService.createOrUpdateCell(row,ind++,followUpDetails.getMedicationDetails().isEmpty()?"":followUpDetails.getMedicationDetails().getFirst().getMedicationName(),cellStyle);
            reportsHelperService.createOrUpdateCell(row,ind++,followUpDetails.getMedicationDetails().isEmpty()?"":followUpDetails.getMedicationDetails().getFirst().getMissedDosages(),cellStyle);
        }

    }


    private void populateTeleCallerRow(Row row, TeleCallerOutputDTO teleCallerOutputDTO, CellStyle cellStyle) {
        reportsHelperService.createOrUpdateCell(row,0,teleCallerOutputDTO.getTeleCallerId(),cellStyle);

        Person person=personMapper.findEverything(teleCallerOutputDTO.getPersonId());
        reportsHelperService.createOrUpdateCell(row,1,person.getFirstName()+" "+person.getLastName(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,2,person.getAddress().getState().getStateName(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,3,person.getEmail(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,4,person.getPhoneNumber(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,5,teleCallerOutputDTO.getDateOfJoining(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,6,personService.getCountOfUsersCreated(teleCallerOutputDTO.getPersonId()),cellStyle);
        reportsHelperService.createOrUpdateCell(row,7,teleCallerOutputDTO.getDateOfLeaving(),cellStyle);
    }

    private void populateStateHeadRow(Row row, StateHeadOutputDTO stateHeadOutputDTO, CellStyle cellStyle) {
        reportsHelperService.createOrUpdateCell(row,0,stateHeadOutputDTO.getStateHeadId(),cellStyle);

        Person person=personMapper.findEverything(stateHeadOutputDTO.getPersonId());
        reportsHelperService.createOrUpdateCell(row,1,person.getFirstName()+" "+person.getLastName(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,2,person.getAddress().getState().getStateName(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,3,person.getEmail(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,4,person.getPhoneNumber(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,5,stateHeadOutputDTO.getDateOfJoining(),cellStyle);
        reportsHelperService.createOrUpdateCell(row,6,personService.getCountOfUsersCreated(stateHeadOutputDTO.getPersonId()),cellStyle);
        reportsHelperService.createOrUpdateCell(row,7,stateHeadOutputDTO.getDateOfLeaving(),cellStyle);

    }

    private void populatePatientRow(Row row, PatientOutputDTO patient, CellStyle cellStyle) {
        CellStyle newCellStyle = row.getSheet().getWorkbook().createCellStyle();
        newCellStyle.cloneStyleFrom(cellStyle);
        if (Boolean.TRUE.equals(patient.getIsDeleted())) {
            newCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        row.setRowStyle(newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 0, patient.getPatientId(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 1, patient.getFirstName() + " " + patient.getLastName(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 2, patient.getGender(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 3, patient.getAge(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 4, patient.getPhoneNumber(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 5, patient.getEmail(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 6, patient.getBlock(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 7, patient.getGp(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 8, patient.getVillage(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 9, patient.getDistrict(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 10, patient.getState(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 11, patientRegistrationService.determinePatientStatus(patient), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 12, patient.getCurrentStatus(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 13, patient.getCreatedAt(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 14, patient.getCreatedBy(), newCellStyle);
        reportsHelperService.createOrUpdateCell(row, 15, patient.getUpdatedBy(), newCellStyle);

        NikshayMitra nikshayMitra = nikshayMitraRepo.findByPatient_Id(patient.getPatientId()).orElse(null);
        if (nikshayMitra != null) {
            reportsHelperService.createOrUpdateCell(row, 16, nikshayMitra.getNikshayId(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 17, nikshayMitra.getUdstStatus(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 18, nikshayMitra.getDateOfUdst(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 19, nikshayMitra.getResultOfUdst(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 20, nikshayMitra.getDbtStatus(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 21, nikshayMitra.getDateOfDbt(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 22, nikshayMitra.getNikshayMitraStatus(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 23, nikshayMitra.getNikshayMitraDate(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 24, nikshayMitra.getNikshayMitraName(), newCellStyle);
        }

        ContactScreening contactScreening = contactScreeningRepos.findByPatient_Id(patient.getPatientId()).orElse(null);
        if (contactScreening != null) {
            reportsHelperService.createOrUpdateCell(row, 25, contactScreening.getContactScreeningDone(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 26, contactScreening.getDateOfContactScreening(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 27, contactScreening.getNoOfHHCsAvailable(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 28, contactScreening.getNoOfHHCsScreened(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 29, contactScreening.getNoOfHHCsWithTBSymptoms(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 30, contactScreening.getNoOfHHCsReferredTBTesting(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 31, contactScreening.getNoOfHHCsDiagnosedTB(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 32, contactScreening.getNoOfHHCsTBInitiatedATT(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 33, contactScreening.getNoOfHHCsUndergoneLTBITest(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 34, contactScreening.getNoOfEligibleForTPT(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 35, contactScreening.getNoOfHHCsInitiatedTPT(), newCellStyle);
        }

        TBDetails tbDetails = tbDetailsRepo.findByPatient_Id(patient.getPatientId()).orElse(null);
        if (tbDetails != null) {
            reportsHelperService.createOrUpdateCell(row, 36, tbDetails.getDateOfDiagnosis(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 37, tbDetails.getDateOfTreatmentInitiation(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 38, tbDetails.getTypeOfPwtb(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 39, tbDetails.getTypeOfTb(), newCellStyle);
            reportsHelperService.createOrUpdateCell(row, 40, tbDetails.getDstbOrDrtb(), newCellStyle);
        }

        PatientFollowUpOutputForFrontEndDto patientFollowUpOutputForFrontEndDto = patientFollowUpService.get(patient.getPatientId());
        int startColumn = 41;
        int temp=-1;
        if (patientFollowUpOutputForFrontEndDto != null) {
            List<FollowUpDetails> followUps = patientFollowUpOutputForFrontEndDto.getFollowUpDetails();
            for (int i = 0; i < 8; i++) {
                int currentColumn = startColumn + (i * 3);
                if (i < followUps.size()) {
                    FollowUpDetails followUp = followUps.get(i);

                    reportsHelperService.createOrUpdateCell(row, currentColumn, followUp.getDate(), newCellStyle);
                    int totalMissed = followUp.getMedicationDetails().stream()
                            .mapToInt(MedicationDetails::getMissedDosages)
                            .sum();
                    reportsHelperService.createOrUpdateCell(row, currentColumn++, totalMissed, newCellStyle);
                    reportsHelperService.createOrUpdateCell(row, currentColumn++, followUp.getFollowUpStatus(), newCellStyle);
                    reportsHelperService.createOrUpdateCell(row,currentColumn++,followUp.getPatientCondition(), newCellStyle);
                } else {
                    reportsHelperService.createOrUpdateCell(row, currentColumn, null, newCellStyle);
                    reportsHelperService.createOrUpdateCell(row, currentColumn++, null, newCellStyle);
                    reportsHelperService.createOrUpdateCell(row, currentColumn++, null, newCellStyle);
                    reportsHelperService.createOrUpdateCell(row, currentColumn++, null, newCellStyle);
                }
                temp=currentColumn;
            }
        }
        startColumn=temp;
        reportsHelperService.createOrUpdateCell(row,startColumn++,patient.getIsDeleted(),newCellStyle);
    }


}
