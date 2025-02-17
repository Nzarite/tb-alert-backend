package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddress(PersonInputDTO addressInputDTO) {
        return Address.builder()
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .state(addressInputDTO.getState())
                .build();
    }

}
