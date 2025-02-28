package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PatientMapper {

    private final PatientRepo patientRepo;
    private LocalDateMapper localDateMapper;

    public Patient find(String patientId) {
        log.info("Mapper called for Find patient with id {}", patientId);

        return patientRepo.findByIdAndPerson_IsDeletedFalse(patientId).orElseThrow(() -> new IllegalArgumentException(
                "Patient with id " + patientId + " not found"));
    }

    public Patient findEverything(String patientId) {

        log.info("Mapper called for Find patient deleted or not with id {}", patientId);

        return patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));
    }

    public PatientOutputDTO toPatientOutputDTO(Patient patient) {
        log.info("Mapper called for toPatientOutputDTO with id {}", patient.getId());

        Person person = patient.getPerson();
        Address address = person.getAddress();

        if (address == null) {
            throw new IllegalArgumentException("Patient with id " + patient.getId() + " not found");
        }
        log.info("Email {}", person.getUpdatedBy());

        return PatientOutputDTO.builder()
                .patientId(patient.getId())
                .personId(person.getId())
                .phoneNumber(person.getPhoneNumber())
                .gender(person.getGender())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .gp(address.getGp())
                .block(address.getBlock())
                .village(address.getVillage())
                .district(address.getDistrict())
                .state(address.getState().getStateName())
                .currentStatus(patient.getCurrentStatus() == null ? "alive" : patient.getCurrentStatus())
                .cured(patient.isCured())
                .createdBy(person.getCreatedBy())
                .createdAt(localDateMapper.toDateTime(person.getCreatedOn()))
                .updatedBy(person.getUpdatedBy())
                .age(patient.getAge())
                .consentForMessage(patient.getConsentForMessage())
                .isDeleted(person.getIsDeleted())
                .build();
    }

}
