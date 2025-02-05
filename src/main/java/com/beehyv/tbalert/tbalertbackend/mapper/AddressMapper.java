package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddress(Patient patient, PatientInputDTO addressInputDTO) {
        return Address.builder()
                .patient(patient)
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .build();
    }

}
