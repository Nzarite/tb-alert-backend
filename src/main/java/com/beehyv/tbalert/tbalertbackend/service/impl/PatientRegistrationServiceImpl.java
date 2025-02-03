package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.mapper.PatientMapper;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepository;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;
    private final AddressRepo addressRepo;
    private final ContactScreeningRepository contactScreeningRepo;

    @Override
    public PatientOutputDTO register(PatientInputDTO patientInputDTO) {
       log.info("Service called to Register patient: {}", patientInputDTO);
        Patient patient=patientMapper.toPatient(patientInputDTO);
        Address address=Address.builder()
                .gp(patientInputDTO.getGp())
                .block(patientInputDTO.getBlock())
                .district(patientInputDTO.getDistrict())
                .village(patientInputDTO.getVillage())
                .patient(patient)
                .build();
        addressRepo.save(address);
        patientRepo.save(patient);
        return patientMapper.toPatientOutputDTO(patient);
    }

    @Override
    public PatientOutputDTO getPatient(int patientId) {
        log.info("Service called to retrieve patient with Id: {}", patientId);
        Patient patient=patientMapper.findPatient(patientId);
        return patientMapper.toPatientOutputDTO(patient);
    }

    @Override
    public void updatePatient(int patientId, PatientInputDTO patientInputDTO) {
        log.info("Service called to update patient with Id: {}", patientId);
        Patient patient=patientMapper.findPatient(patientId);
        patient.setFirstName(patientInputDTO.getFirstName());
        patient.setLastName(patientInputDTO.getLastName());
        patient.setGender(patientInputDTO.getGender());
        patient.setPhone(patientInputDTO.getPhone());

        Address address=addressRepo.findByPatient(patient);
        address.setGp(patientInputDTO.getGp());
        address.setBlock(patientInputDTO.getBlock());
        address.setDistrict(patientInputDTO.getDistrict());
        address.setVillage(patientInputDTO.getVillage());
        addressRepo.save(address);
        patientRepo.save(patient);
    }

    @Override
    public void deletePatient(int patientId) {
        log.info("Service called to delete patient with Id: {}", patientId);
        Patient patient=patientMapper.findPatient(patientId);
        addressRepo.delete(addressRepo.findByPatient(patient));
        contactScreeningRepo.deleteByPatientId(patientId);
        patientRepo.delete(patient);
    }

    @Override
    public List<PatientOutputDTO> getAll() {
        log.info("Service getAll patients");
        List<Patient>patients=patientRepo.findAll();
        if(!patients.isEmpty()){
            return patients.stream().map(patientMapper::toPatientOutputDTO).collect(Collectors.toList());
        }
        return null;
    }

}
