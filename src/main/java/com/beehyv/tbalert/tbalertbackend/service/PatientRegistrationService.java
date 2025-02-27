package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;

import java.util.List;
import java.util.Map;

public interface PatientRegistrationService {

    PatientOutputDTO register(PatientInputDTO patientInputDTO);

    PatientOutputDTO getPatient(String patientId);

    void updatePatient(String patientId, PatientUpdateInputDTO patientUpdateInputDTO);

    void deletePatient(String patientId);

    List<PatientOutputDTO> getAllNotDeleted();

    List<PatientOutputDTO> getAll();

    List<PatientOutputDTO> getPatientByNameOrNikshayIdOrPatientId(String patientName);

    List<PatientOutputDTO> getFilteredPatients(Map<String, Object> filters);

    String determinePatientStatus(PatientOutputDTO patientOutputDTO);

    List<PatientOutputDTO> getAllByState(String state);
}
