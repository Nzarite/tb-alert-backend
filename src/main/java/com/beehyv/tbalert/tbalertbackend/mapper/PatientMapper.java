package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientMapper {

    private final PatientRepo patientRepo;

    public Patient findPatient(int patientId) {
        return patientRepo.findById(patientId).orElseThrow(()-> new IllegalArgumentException("" +
                "Patient with id " + patientId + " not found"));
    }

    public PatientOutputDTO toPatientOutputDTO(Patient patient) {
        return PatientOutputDTO.builder()
                .phone(patient.getPhone())
                .gender(patient.getGender())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .build();
    }

    public Patient toPatient(PatientInputDTO patientInputDTO) {
        return Patient.builder()
                .firstName(patientInputDTO.getFirstName())
                .lastName(patientInputDTO.getLastName())
                .gender(patientInputDTO.getGender())
                .phone(patientInputDTO.getPhone())
                .build();
    }
}
