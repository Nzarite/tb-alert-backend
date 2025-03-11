package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.repository.TeleCallerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeleCallerMapper {

    private final TeleCallerRepo teleCallerRepo;
    private final LocalDateMapper localDateMapper;

    public TeleCaller find(Long id) {
        return teleCallerRepo.findById(id).filter(teleCaller -> !teleCaller.getPerson().getIsDeleted()).orElseThrow(()-> new IllegalArgumentException("TeleCaller not found for id: " + id));
    }

    public TeleCallerOutputDTO toTeleCallerOutputDTO(TeleCaller teleCaller)
    {
        Person person = teleCaller.getPerson();
        Address address=person.getAddress();
        GramPanchayat gramPanchayat=address.getGramPanchayat();
        Mandal mandal = gramPanchayat.getMandal();
        District district=mandal.getDistrict();
        State state = district.getState();

        return TeleCallerOutputDTO.builder()
                .teleCallerId(teleCaller.getId())
                .personId(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .gender(person.getGender())
                .state(state.getStateName())
                .gp(gramPanchayat.getName())
                .village(address.getVillage())
                .district(district.getName())
                .block(mandal.getName())
                .createdBy(person.getCreatedBy())
                .createdOn(localDateMapper.toDateTime(person.getCreatedOn()))
                .updatedBy(person.getUpdatedBy())
                .dateOfJoining(localDateMapper.toDate(teleCaller.getDateOfJoining()))
                .dateOfLeaving(localDateMapper.toDate(teleCaller.getDateOfLeaving()))
                .build();
    }
}
