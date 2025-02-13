package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
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

        Person person = patient.getPerson();
        Address address=addressRepo.findByPerson(person);

        if(address==null) {
            throw new IllegalArgumentException("Patient with id " + patient.getId() + " not found");
        }
        log.info("Email {}", person.getUpdatedBy());

        return PatientOutputDTO.builder()
                .patientId(patient.getId())
                .personId(person.getId())
                .phone(person.getPhoneNumber())
                .gender(person.getGender())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth().toString())
                .email(person.getEmail())
                .gp(address.getGp())
                .block(address.getBlock())
                .village(address.getVillage())
                .district(address.getDistrict())
                .currentStatus(patient.getCurrentStatus()==null?"alive":patient.getCurrentStatus())
                .cured(patient.isCured())
                .createdBy(person.getCreatedBy())
                .createdAt(localDateMapper.toDateTime(person.getCreatedOn()))
                .updatedBy(person.getUpdatedBy())
                .build();
    }

    public Patient toPatient(Person person, PatientInputDTO patientInputDTO) {
        log.info("Mapper called for toPatient from PatientInputDTO: {}", patientInputDTO);
        return Patient.builder()
                .person(person)
                .currentStatus(patientInputDTO.getCurrentStatus())
                .build();
    }
}
