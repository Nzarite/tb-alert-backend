package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto.FollowUpDetails;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.mapper.*;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientFollowUpRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private MissedMedicationService missedMedicationService;
    private final PatientRepo patientRepo;
    private final MissedMedicationMapper missedMedicationMapper;
    private final LocalDateMapper localDateMapper;
    private final MissedMedicationService medicationService;


    @Override
    public PatientFollowUpOutputForFrontEndDto get(int id) {
        log.info("Service called to Get patient follow up with patient id {}", id);
        List<PatientFollowUp> patientFollowUps = patientFollowUpRepo.findByPatient_Id(id);
        if(patientFollowUps.isEmpty()) {
            throw new IllegalArgumentException("No patient follow up for patient id " + id);
        }
        Patient patient = patientFollowUps.getFirst().getPatient();
        List<PatientFollowUpOutputForFrontEndDto.FollowUpDetails>followUpDetails=new ArrayList<>();

        List<PatientMedication>patientMedications=patientMedicationRepo.getPatientMedicationsByPatient(patient);

        patientFollowUps.forEach(patientFollowUp -> {
            List<MissedMedication>missedMedicationList=new ArrayList<>();
            patientMedications.forEach(patientMedication -> {
                List<MissedMedication>missedMedications=missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,patientFollowUp.getDate());
                missedMedicationList.addAll(missedMedications);
            });
            FollowUpDetails followUpDetail=patientFollowUpMapper.toFollowUpDetails(patientFollowUp,missedMedicationList);
            followUpDetails.add(followUpDetail);
        });
        return PatientFollowUpOutputForFrontEndDto.builder()
                .followUpDetails(followUpDetails)
                .patient(patientMapper.toPatientOutputDTO(patient))
                .build();
    }

    @Override
    public PatientFollowUpOutputDTO add(int id, PatientFollowUpInputDTO patientFollowUpInputDTO) {
        log.info("Service called to Add patient follow up with patient id {}", id);
        Patient patient=patientMapper.findPatient(id);
        List<MissedMedication>missedMedicationList=missedMedicationMapper.findMissedMedicationsByPatientandDate(patient,localDateMapper.toLocalDate(patientFollowUpInputDTO.getDate()));
        PatientFollowUp patientFollowUp=patientFollowUpMapper.toPatientFollowUp(patientFollowUpInputDTO,patient);
        patientFollowUpRepo.save(patientFollowUp);
        return patientFollowUpMapper.toDTO(patientFollowUp,missedMedicationList);
    }

    @Override
    public PatientFollowUpOutputDTO update(int id, PatientFollowUpInputDTO patientFollowUpInputDTO) {
        LocalDate date=localDateMapper.toLocalDate(patientFollowUpInputDTO.getDate());
        missedMedicationService.add(id,patientFollowUpInputDTO.getMissedMedications(),date);
        Patient patient=patientMapper.findPatient(id);
        PatientFollowUp patientFollowUp=patientFollowUpRepo.findByPatientAndDate(patient,date);
        if(patientFollowUp==null){
            throw new IllegalArgumentException("No follow up exists for the given patient and date");
        }
        patientFollowUp.setRemarks(patientFollowUpInputDTO.getRemarks());
        patientFollowUp.setDate(date);
        if(patientFollowUpInputDTO.getCurrentStatus().equals("dead")){
            patient.setCurrentStatus(patientFollowUpInputDTO.getCurrentStatus());
            patientRepo.save(patient);
        }
        if(patientFollowUpInputDTO.isCured()){
            patient.setCured(true);
            patientRepo.save(patient);
        }
        patientFollowUp.setOccured(true);
        patientFollowUpRepo.save(patientFollowUp);
        List<MissedMedication>missedMedications=missedMedicationMapper.findMissedMedicationsByPatientandDate(patient,patientFollowUp.getDate());
        return patientFollowUpMapper.toDTO(patientFollowUp,missedMedications);
    }

    @Override
    public List<PatientFollowUp> findBeforeDate(int id, LocalDate localDate) {
        Patient patient=patientMapper.findPatient(id);
        return patientFollowUpRepo.findByPatientAndDateBefore(patient,localDate);
    }
}
