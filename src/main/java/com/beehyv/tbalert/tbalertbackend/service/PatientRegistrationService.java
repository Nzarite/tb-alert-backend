package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;

import java.util.List;
import java.util.Map;

public interface PatientRegistrationService {

    PatientOutputDTO register(PatientInputDTO patientInputDTO);

    PatientOutputDTO getPatient(int patientId);

    void updatePatient(int patientId, PatientInputDTO patientInputDTO);

    void deletePatient(int patientId);

    List<PatientOutputDTO> getAll();

    List<PatientOutputDTO> getPatientByName(String patientName);

    List<PatientOutputDTO>getFilteredPatients(Map<String,Object> filters);

}
