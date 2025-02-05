package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.MedicationDetailsMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientFollowUpMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientFollowUpRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatientFollowUpServiceImpl implements PatientFollowUpService {

    private final PatientFollowUpRepo patientFollowUpRepo;
    private final PatientMapper patientMapper;
    private final MissedMedicationRepo missedMedicationRepo;
    private final PatientMedicationRepo patientMedicationRepo;
    private final PatientFollowUpMapper patientFollowUpMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public List<PatientFollowUpOutputDTO> get(int id) {
        log.info("Service called to Get patient follow up with patient id {}", id);
        List<PatientFollowUp> patientFollowUps = patientFollowUpRepo.findByPatient_Id(id);
        List<PatientFollowUpOutputDTO>patientFollowUpOutputDTOS = new ArrayList<>();
        patientFollowUps.forEach(patientFollowUp -> {
            List<PatientMedication>patientMedications=patientMedicationRepo.getPatientMedicationsByPatient(patientFollowUp.getPatient());
            List<MissedMedication>missedMedicationList=new ArrayList<>();
            patientMedications.forEach(patientMedication -> {
                List<MissedMedication>missedMedications=missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,patientFollowUp.getDate());
                missedMedicationList.addAll(missedMedications);
            });
            patientFollowUpOutputDTOS.add(patientFollowUpMapper.toDTO(patientFollowUp,missedMedicationList));
        });
        return patientFollowUpOutputDTOS;
    }

    @Override
    public PatientFollowUpOutputDTO add(int id, PatientFollowUpInputDTO patientFollowUpInputDTO) {
        log.info("Service called to Add patient follow up with patient id {}", id);
        Patient patient=patientMapper.findPatient(id);
        List<PatientMedication> patientMedications=patientMedicationRepo.findPatientMedicationByPatient(patient);
        List<MissedMedication> missedMedicationList=new ArrayList<>();
        patientMedications.forEach(patientMedication -> {
            missedMedicationList.addAll((missedMedicationRepo.findByPatientMedicationAndDate(patientMedication, LocalDate.parse(patientFollowUpInputDTO.getDate(), formatter))));
        });
        PatientFollowUp patientFollowUp=patientFollowUpMapper.toPatientFollowUp(patientFollowUpInputDTO,patient,missedMedicationList);
        patientFollowUpRepo.save(patientFollowUp);
        return patientFollowUpMapper.toDTO(patientFollowUp,missedMedicationList);
    }
}
