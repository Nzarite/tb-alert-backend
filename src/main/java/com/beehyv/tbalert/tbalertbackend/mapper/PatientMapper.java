package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PatientMapper {

    private final PatientRepo patientRepo;
    private final AddressRepo addressRepo;
    private LocalDateMapper localDateMapper;

    public Patient findPatient(int patientId) {
        log.info("Mapper called for Find patient with id {}", patientId);
        return patientRepo.findById(patientId).orElseThrow(()-> new IllegalArgumentException(
                "Patient with id " + patientId + " not found"));
    }

    public PatientOutputDTO toPatientOutputDTO(Patient patient) {
        log.info("Mapper called for toPatientOutputDTO with id {}", patient.getId());
        Address address=addressRepo.findByPatient(patient);
        if(address==null) {
            throw new IllegalArgumentException("Patient with id " + patient.getId() + " not found");
        }
        return PatientOutputDTO.builder()
                .patientId(patient.getId())
                .phone(patient.getPhone())
                .gender(patient.getGender())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .gp(address.getGp())
                .block(address.getBlock())
                .village(address.getVillage())
                .district(address.getDistrict())
                .currentStatus(patient.getCurrentStatus())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .build();
    }

    public Patient toPatient(PatientInputDTO patientInputDTO) {
        log.info("Mapper called for toPatient from PatientInputDTO: {}", patientInputDTO);
        return Patient.builder()
                .firstName(patientInputDTO.getFirstName())
                .lastName(patientInputDTO.getLastName())
                .gender(patientInputDTO.getGender())
                .phone(patientInputDTO.getPhone())
                .dateOfBirth(localDateMapper.toLocalDate(patientInputDTO.getDateOfBirth()))
                .build();
    }
}
