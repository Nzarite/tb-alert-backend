package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final PersonRepo personRepo;
    private final LocalDateMapper localDateMapper;

    public Person find(Long id)
    {
        return personRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()->new IllegalArgumentException("Invalid Person Id " +id));
    }


    public Person findEverything(Long id)
    {
        return personRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid Person Id " +id));
    }

    public <T> Person toPerson(T personInputDTO) {
        String firstName = getField(personInputDTO, "getFirstName");
        String lastName = getField(personInputDTO, "getLastName");
        String email = getField(personInputDTO, "getEmail");
        String phoneNumber = getField(personInputDTO, "getPhoneNumber");
        String gender = getField(personInputDTO, "getGender");
        String createdBy = getField(personInputDTO, "getCreatedBy");

        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .createdOn(LocalDateTime.now())
                .createdBy(createdBy)
                .isDeleted(false)
                .build();
    }

    private <T> String getField(T dto, String methodName) {
        try {
            return (String) dto.getClass().getMethod(methodName).invoke(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error accessing field " + methodName, e);
        }
    }

//    public Person toPerson(PersonInputDTO personInputDTO)
//    {
//        return Person.builder()
//                .firstName(personInputDTO.getFirstName())
//                .lastName(personInputDTO.getLastName())
//                .email(personInputDTO.getEmail())
//                .phoneNumber(personInputDTO.getPhoneNumber())
//                .gender(personInputDTO.getGender())
//                .createdOn(LocalDateTime.now())
//                .createdBy(personInputDTO.getCreatedBy())
//                .isDeleted(false)
//                .build();
//    }
//    public Person toPerson(TeleCallerInputDTO personInputDTO)
//    {
//        return Person.builder()
//                .firstName(personInputDTO.getFirstName())
//                .lastName(personInputDTO.getLastName())
//                .email(personInputDTO.getEmail())
//                .phoneNumber(personInputDTO.getPhoneNumber())
//                .gender(personInputDTO.getGender())
//                .createdOn(LocalDateTime.now())
//                .createdBy(personInputDTO.getCreatedBy())
//                .isDeleted(false)
//                .build();
//    }
//
//    public Person toPerson(StateHeadInputDTO personInputDTO)
//    {
//        return Person.builder()
//                .firstName(personInputDTO.getFirstName())
//                .lastName(personInputDTO.getLastName())
//                .email(personInputDTO.getEmail())
//                .phoneNumber(personInputDTO.getPhoneNumber())
//                .gender(personInputDTO.getGender())
//                .createdOn(LocalDateTime.now())
//                .createdBy(personInputDTO.getCreatedBy())
//                .isDeleted(false)
//                .build();
//    }
//    public Person toPerson(PatientInputDTO patientInputDTO)
//    {
//        return Person.builder()
//                .firstName(patientInputDTO.getFirstName())
//                .lastName(patientInputDTO.getLastName())
//                .email(patientInputDTO.getEmail())
//                .phoneNumber(patientInputDTO.getPhoneNumber())
//                .gender(patientInputDTO.getGender())
//                .createdOn(LocalDateTime.now())
//                .createdBy(patientInputDTO.getCreatedBy())
//                .isDeleted(false)
//                .build();
//    }

    public PersonOutputDTO toPersonOutputDTO(Person person)
    {
        Address address=person.getAddress();
        GramPanchayat gramPanchayat=address.getGramPanchayat();
        Mandal mandal=gramPanchayat.getMandal();
        District district=mandal.getDistrict();
        State state=district.getState();

        return PersonOutputDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .gender(person.getGender())
                .state(state.getStateName())
                .gp(gramPanchayat.getName())
                .mandal(mandal.getName())
                .district(district.getName())
                .village(address.getVillage())
                .createdBy(person.getCreatedBy())
                .createdOn(localDateMapper.toDateTime(person.getCreatedOn()))
                .updatedBy(person.getUpdatedBy())
                .build();
    }
}
