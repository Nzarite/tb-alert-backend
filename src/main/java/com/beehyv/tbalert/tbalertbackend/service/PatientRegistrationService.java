package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import jakarta.validation.Valid;

public interface PatientRegistrationService {

    void register(@Valid PatientInputDTO patientInputDTO);

    PatientOutputDTO getPatient(int patientId);

    void updatePatient(int patientId, @Valid PatientInputDTO patientInputDTO);

    void deletePatient(int patientId);
}
