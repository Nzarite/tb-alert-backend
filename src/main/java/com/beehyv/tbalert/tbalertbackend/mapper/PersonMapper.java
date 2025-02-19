package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final PersonRepo personRepo;
    private final LocalDateMapper localDateMapper;
    private final AddressRepo addressRepo;

    public Person find(Long id)
    {
        return personRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid Person Id " +id));
    }

    public Person toPerson(PersonInputDTO personInputDTO)
    {
        return Person.builder()
                .firstName(personInputDTO.getFirstName())
                .lastName(personInputDTO.getLastName())
                .email(personInputDTO.getEmail())
                .phoneNumber(personInputDTO.getPhoneNumber())
                .gender(personInputDTO.getGender())
                .createdOn(LocalDateTime.now())
                .createdBy(personInputDTO.getCreatedBy())
                .build();
    }
    public Person toPerson(TeleCallerInputDTO personInputDTO)
    {
        return Person.builder()
                .firstName(personInputDTO.getFirstName())
                .lastName(personInputDTO.getLastName())
                .email(personInputDTO.getEmail())
                .phoneNumber(personInputDTO.getPhoneNumber())
                .gender(personInputDTO.getGender())
                .createdOn(LocalDateTime.now())
                .createdBy(personInputDTO.getCreatedBy())
                .build();
    }

    public Person toPerson(StateHeadInputDTO personInputDTO)
    {
        return Person.builder()
                .firstName(personInputDTO.getFirstName())
                .lastName(personInputDTO.getLastName())
                .email(personInputDTO.getEmail())
                .phoneNumber(personInputDTO.getPhoneNumber())
                .gender(personInputDTO.getGender())
                .createdOn(LocalDateTime.now())
                .createdBy(personInputDTO.getCreatedBy())
                .build();
    }
    public Person toPerson(PatientInputDTO patientInputDTO)
    {
        return Person.builder()
                .firstName(patientInputDTO.getFirstName())
                .lastName(patientInputDTO.getLastName())
                .email(patientInputDTO.getEmail())
                .phoneNumber(patientInputDTO.getPhoneNumber())
                .gender(patientInputDTO.getGender())
                .createdOn(LocalDateTime.now())
                .createdBy(patientInputDTO.getCreatedBy())
                .build();
    }

    public PersonOutputDTO toPersonOutputDTO(Person person)
    {
        Address address=person.getAddress();
        return PersonOutputDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .gender(person.getGender())
                .state(address.getState())
                .gp(address.getGp())
                .block(address.getBlock())
                .district(address.getDistrict())
                .village(address.getVillage())
                .createdBy(person.getCreatedBy())
                .createdOn(localDateMapper.toDateTime(person.getCreatedOn()))
                .updatedBy(person.getUpdatedBy())
                .build();
    }
}
