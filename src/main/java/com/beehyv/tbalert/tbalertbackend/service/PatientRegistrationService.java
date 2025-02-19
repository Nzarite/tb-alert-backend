package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;

import java.util.List;
import java.util.Map;

public interface PatientRegistrationService {

    PatientOutputDTO register(PersonInputDTO personInputDTO);

    PatientOutputDTO getPatient(int patientId);

    void updatePatient(int patientId, PatientUpdateInputDTO patientUpdateInputDTO);

    void deletePatient(int patientId);

    List<PatientOutputDTO> getAll();

    List<PatientOutputDTO> getPatientByName(String patientName);

    List<PatientOutputDTO>getFilteredPatients(Map<String,Object> filters);

    String determinePatientStatus(PatientOutputDTO patientOutputDTO);
}
