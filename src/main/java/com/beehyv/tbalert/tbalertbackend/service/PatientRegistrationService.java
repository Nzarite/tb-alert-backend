package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientRegistrationService {

    PatientOutputDTO register(@Valid PatientInputDTO patientInputDTO);

    PatientOutputDTO getPatient(int patientId);

    void updatePatient(int patientId, @Valid PatientInputDTO patientInputDTO);

    void deletePatient(int patientId);

    List<PatientOutputDTO> getAll();
}
