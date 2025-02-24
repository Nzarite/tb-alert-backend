package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.TeleCallerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeleCallerMapper {

    private final TeleCallerRepo teleCallerRepo;
    private final LocalDateMapper localDateMapper;
    private final AddressMapper addressMapper;
    private final AddressRepo addressRepo;

    public TeleCaller find(Long id) {
        return teleCallerRepo.findById(id).filter(teleCaller -> !teleCaller.getPerson().getIsDeleted()).orElseThrow(()-> new IllegalArgumentException("TeleCaller not found for id: " + id));
    }

    public TeleCallerOutputDTO toTeleCallerOutputDTO(TeleCaller teleCaller)
    {
        Person person = teleCaller.getPerson();
        Address address=person.getAddress();

        return TeleCallerOutputDTO.builder()
                .teleCallerId(teleCaller.getId())
                .personId(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .gender(person.getGender())
                .block(address.getBlock())
                .village(address.getVillage())
                .gp(address.getGp())
                .district(address.getDistrict())
                .state(address.getState().getStateName())
                .createdBy(person.getCreatedBy())
                .createdOn(localDateMapper.toDateTime(person.getCreatedOn()))
                .updatedBy(person.getUpdatedBy())
                .dateOfJoining(localDateMapper.toDate(teleCaller.getDateOfJoining()))
                .build();
    }
}
