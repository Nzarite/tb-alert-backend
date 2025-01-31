package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;

    @Override
    public void register(PatientInputDTO patientInputDTO) {
        Patient patient=patientMapper.toPatient(patientInputDTO);
        patientRepo.save(patient);
    }

    @Override
    public PatientOutputDTO getPatient(int patientId) {
        Patient patient=patientMapper.findPatient(patientId);
        return patientMapper.toPatientOutputDTO(patient);
    }

    @Override
    public void updatePatient(int patientId, PatientInputDTO patientInputDTO) {
        Patient patient=patientMapper.findPatient(patientId);
        patient.setFirstName(patientInputDTO.getFirstName());
        patient.setLastName(patientInputDTO.getLastName());
        patient.setGender(patientInputDTO.getGender());
        patient.setPhone(patientInputDTO.getPhone());
        patientRepo.save(patient);
    }

    @Override
    public void deletePatient(int patientId) {
        Patient patient=patientMapper.findPatient(patientId);
        patientRepo.delete(patient);
    }
}
