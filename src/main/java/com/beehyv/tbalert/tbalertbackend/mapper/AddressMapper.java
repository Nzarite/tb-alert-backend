package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddress(Person person, PersonInputDTO addressInputDTO) {
        return Address.builder()
                .person(person)
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .state(addressInputDTO.getState())
                .build();
    }

}
