package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;
    private final AddressRepo addressRepo;

    @Override
    public PatientOutputDTO register(PatientInputDTO patientInputDTO) {
        Patient patient=patientMapper.toPatient(patientInputDTO);
        Patient savedPatient = patientRepo.save(patient);

        Address address=Address.builder()
                .gp(patientInputDTO.getGp())
                .block(patientInputDTO.getBlock())
                .district(patientInputDTO.getDistrict())
                .village(patientInputDTO.getVillage())
                .patient(savedPatient)
                .build();
        addressRepo.save(address);

        return patientMapper.toPatientOutputDTO(patient);
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
        Patient updatedPatient = patientRepo.save(patient);

        Address address=addressRepo.findByPatient(patient);
        address.setGp(patientInputDTO.getGp());
        address.setBlock(patientInputDTO.getBlock());
        address.setDistrict(patientInputDTO.getDistrict());
        address.setVillage(patientInputDTO.getVillage());
        address.setPatient(updatedPatient);
        addressRepo.save(address);

    }

    @Override
    public void deletePatient(int patientId) {
        Patient patient=patientMapper.findPatient(patientId);
        addressRepo.delete(addressRepo.findByPatient(patient));
        patientRepo.delete(patient);
    }

    @Override
    public List<PatientOutputDTO> getAll() {
        List<Patient>patients=patientRepo.findAll();
        if(!patients.isEmpty()){
            return patients.stream().map(patientMapper::toPatientOutputDTO).collect(Collectors.toList());
        }
        return null;
    }

}
