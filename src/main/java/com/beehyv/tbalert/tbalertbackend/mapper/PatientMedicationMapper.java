package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.repository.PatientMedicationRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PatientMedicationMapper {

    private final PatientMapper patientMapper;
    private final MedicationMapper medicationMapper;
    private final PatientMedicationRepo patientMedicationRepo;

    public PatientMedication find(int id)
    {
        log.info("Mapper called for Find patient medication by id: {}", id);
        return patientMedicationRepo.findById(id).orElseThrow(()-> new RuntimeException("Patient Medication not found"));
    }

    public PatientMedication toPatientMedication(int id,PatientMedicationInputDTO patientMedication) {
        log.info("Mapper called for toPatientMedication from PatientMedcicatonInputDTO: {}", patientMedication);
        Patient patient = patientMapper.findPatient(id);
        Medication medication = medicationMapper.findMedicationById(patientMedication.getMedicationId());
        return PatientMedication.builder()
                .medication(medication)
                .patient(patient)
                .frequency(patientMedication.getFrequency())
                .build();
    }

    public PatientMedicationOutputDTO toPatientMedicationOutputDTO(PatientMedication patientMedication) {
        log.info("Mapper called for toPatientMedicationOutputDTO from PatientMedication: {}", patientMedication);
        return PatientMedicationOutputDTO.builder()
                .patient(patientMedication.getPatient())
                .medicationId(patientMedication.getMedication().getId())
                .medication(patientMedication.getMedication().getName())
                .frequency(patientMedication.getFrequency())
                .build();
    }

}
