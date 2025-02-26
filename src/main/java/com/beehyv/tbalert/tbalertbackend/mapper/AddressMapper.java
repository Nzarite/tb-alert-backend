package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.repository.StateRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressMapper {

    private final StateMapper stateMapper;

    public <T> Address toAddress(T addressInputDTO) {
        String village = getField(addressInputDTO, "getVillage");
        String gp = getField(addressInputDTO, "getGp");
        String block = getField(addressInputDTO, "getBlock");
        String district = getField(addressInputDTO, "getDistrict");
        String stateName = getField(addressInputDTO, "getState");

        return Address.builder()
                .village(village)
                .gp(gp)
                .block(block)
                .district(district)
                .state(stateMapper.getStateByName(stateName))
                .build();
    }

    private <T> String getField(T dto, String methodName) {
        try {
            return (String) dto.getClass().getMethod(methodName).invoke(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error accessing field " + methodName, e);
        }
    }

//    public Address toAddress(PatientInputDTO addressInputDTO) {
//        return Address.builder()
//                .village(addressInputDTO.getVillage())
//                .gp(addressInputDTO.getGp())
//                .block(addressInputDTO.getBlock())
//                .district(addressInputDTO.getDistrict())
//                .state(stateMapper.getStateByName(addressInputDTO.getState()))
//                .build();
//    }
//
//    public Address toAddress(PersonInputDTO addressInputDTO) {
//        return Address.builder()
//                .village(addressInputDTO.getVillage())
//                .gp(addressInputDTO.getGp())
//                .block(addressInputDTO.getBlock())
//                .district(addressInputDTO.getDistrict())
//                .state(stateMapper.getStateByName(addressInputDTO.getState()))
//                .build();
//    }
//
//    public Address toAddress(TeleCallerInputDTO addressInputDTO) {
//        return Address.builder()
//                .village(addressInputDTO.getVillage())
//                .gp(addressInputDTO.getGp())
//                .block(addressInputDTO.getBlock())
//                .district(addressInputDTO.getDistrict())
//                .state(stateMapper.getStateByName(addressInputDTO.getState()))
//                .build();
//    }
//    public Address toAddress(StateHeadInputDTO addressInputDTO) {
//        return Address.builder()
//                .village(addressInputDTO.getVillage())
//                .gp(addressInputDTO.getGp())
//                .block(addressInputDTO.getBlock())
//                .district(addressInputDTO.getDistrict())
//                .state(stateMapper.getStateByName(addressInputDTO.getState()))
//                .build();
//    }
}
