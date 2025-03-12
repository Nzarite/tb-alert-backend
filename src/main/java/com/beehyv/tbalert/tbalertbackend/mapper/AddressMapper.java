package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressMapper {

    private final StateMapper stateMapper;
    private final GramPanchayatMapper gramPanchayatMapper;

    public <T> Address toAddress(T addressInputDTO) {
        String village = getField(addressInputDTO, "getVillage");
        Long gp = Long.parseLong(getField(addressInputDTO, "getGramPanchayatId"));
        GramPanchayat gramPanchayat=gramPanchayatMapper.getGramPanchayatById(gp);

        return Address.builder()
                .village(village)
                .gramPanchayat(gramPanchayat)
                .build();
    }

    private <T> String getField(T dto, String methodName) {
        try {
            return (String) dto.getClass().getMethod(methodName).invoke(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error accessing field " + methodName, e);
        }
    }

/*
    public Address toAddress(PatientInputDTO addressInputDTO) {
        return Address.builder()
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .state(stateMapper.getStateByName(addressInputDTO.getState()))
                .build();
    }

    public Address toAddress(PersonInputDTO addressInputDTO) {
        return Address.builder()
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .state(stateMapper.getStateByName(addressInputDTO.getState()))
                .build();
    }

    public Address toAddress(TeleCallerInputDTO addressInputDTO) {
        return Address.builder()
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .state(stateMapper.getStateByName(addressInputDTO.getState()))
                .build();
    }
    public Address toAddress(StateHeadInputDTO addressInputDTO) {
        return Address.builder()
                .village(addressInputDTO.getVillage())
                .gp(addressInputDTO.getGp())
                .block(addressInputDTO.getBlock())
                .district(addressInputDTO.getDistrict())
                .state(stateMapper.getStateByName(addressInputDTO.getState()))
                .build();
    }
*/
}
