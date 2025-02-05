package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.repository.MissedMedicationRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MissedMedicationMapper {

    private final MissedMedicationRepo missedMedicationRepo;
    private final PatientMedicationRepo patientMedicationRepo;

    public MissedMedication toMissedMedication(MissedMedicationInputDTO missedMedicationInputDTO, PatientMedication patientMedication,LocalDate date) {
        log.info("Mapper called for toMissedMedication from missedMedicationInputDTO = {}", missedMedicationInputDTO);
        return MissedMedication.builder()
                .patientMedication(patientMedication)
                .missedDosages(missedMedicationInputDTO.getMissedDoses())
                .comment(missedMedicationInputDTO.getComment())
                .date(date)
                .build();
    }

    public MissedMedicationOutputDTO toMissedMedicationOutputDTO(MissedMedication missedMedication) {
        log.info("Mapper called for toMissedMedicationOutputDTO from missedMedication = {}", missedMedication);
        return MissedMedicationOutputDTO.builder()
                .medication(missedMedication.getPatientMedication().getMedication())
                .patient(missedMedication.getPatientMedication().getPatient())
                .missedDoses(missedMedication.getMissedDosages())
                .comment(missedMedication.getComment())
                .date(missedMedication.getDate())
                .build();
    }

    public List<MissedMedication> findMissedMedicationsByPatientandDate(Patient patient,LocalDate date) {
        List<PatientMedication>patientMedicationList=patientMedicationRepo.findPatientMedicationByPatient(patient);
        if(patientMedicationList.isEmpty()){
            throw new IllegalArgumentException("Patient medication list is empty for Patient: "+patient);
        }
        List<MissedMedication>missedMedications=new ArrayList<>();
        patientMedicationList.forEach(patientMedication -> {
            missedMedications.addAll(missedMedicationRepo.findByPatientMedicationAndDate(patientMedication,date));
        });
        return missedMedications;
    }
}
