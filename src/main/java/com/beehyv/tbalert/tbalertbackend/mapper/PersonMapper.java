package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final PersonRepo personRepo;

    public Person find(Long id)
    {
        return personRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid Person Id " +id));
    }

    public Person toPerson(PersonInputDTO personInputDTO)
    {
        return Person.builder()
                .firstName(personInputDTO.getFirstName())
                .lastName(personInputDTO.getLastName())
                .State(personInputDTO.getState())
                .Role(personInputDTO.getRole())
                .email(personInputDTO.getEmail())
                .phoneNumber(personInputDTO.getPhoneNumber())
                .gender(personInputDTO.getGender())
                .build();
    }

    public PersonOutputDTO toPersonOutputDTO(Person person)
    {
        return PersonOutputDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .State(person.getState())
                .phoneNumber(person.getPhoneNumber())
                .Role(person.getRole())
                .gender(person.getGender())
                .build();
    }
}
