package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.entity.StateHead;
import com.beehyv.tbalert.tbalertbackend.repository.StateHeadRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StateHeadMapper {

    private final StateHeadRepo stateHeadRepo;
    private final LocalDateMapper localDateMapper;

    public StateHead find(long id)
    {
        log.info("Mapper called to find StateHead with id {}", id);
        return stateHeadRepo.findById(id).orElseThrow(()->new IllegalArgumentException("State head not found for Id: "+id));
    }

    public StateHeadOutputDTO toStateHeadOutputDTO(StateHead stateHead) {
        Person person = stateHead.getPerson();
        Address address = person.getAddress();
        return StateHeadOutputDTO.builder()
                        .stateHeadId(stateHead.getId())
                        .personId(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .email(person.getEmail())
                        .gender(person.getGender())
                        .createdBy(person.getCreatedBy())
                        .createdOn(localDateMapper.toDateTime(person.getCreatedOn()))
                        .phoneNumber(person.getPhoneNumber())
                        .state(address.getState())
                        .gp(address.getGp())
                        .village(address.getVillage())
                        .district(address.getDistrict())
                        .block(address.getBlock())
                        .updatedBy(person.getUpdatedBy())
                        .dateOfJoining(localDateMapper.toDate(stateHead.getDateOfJoining()))
                        .build();
    }
}
