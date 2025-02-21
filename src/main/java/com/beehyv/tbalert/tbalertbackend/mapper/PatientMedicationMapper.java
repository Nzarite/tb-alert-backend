package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PatientMedicationMapper {

    private final PatientMapper patientMapper;
    private final MedicationMapper medicationMapper;

    public PatientMedication toPatientMedication(String id,PatientMedicationInputDTO patientMedication) {
        log.info("Mapper called for toPatientMedication from PatientMedcicatonInputDTO: {}", patientMedication);
        Patient patient = patientMapper.find(id);
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
                .medicationId(patientMedication.getMedication().getId())
                .medicationName(patientMedication.getMedication().getName())
                .frequency(patientMedication.getFrequency())
                .build();
    }

}
