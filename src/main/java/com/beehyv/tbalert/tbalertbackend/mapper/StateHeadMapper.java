package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
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

    public StateHead find(long id) {
        log.info("Mapper called to find StateHead with id {}", id);
        return stateHeadRepo.findById(id).filter(stateHead -> !stateHead.getPerson().getIsDeleted()).orElseThrow(() -> new IllegalArgumentException("State head not found for Id: " + id));
    }

    public StateHeadOutputDTO toStateHeadOutputDTO(StateHead stateHead) {
        Person person = stateHead.getPerson();
        Address address = person.getAddress();
        GramPanchayat gramPanchayat=address.getGramPanchayat();
        Mandal mandal = gramPanchayat.getMandal();
        District district=mandal.getDistrict();
        State state = district.getState();

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
                .state(state.getStateName())
                .gp(gramPanchayat.getName())
                .village(address.getVillage())
                .district(district.getName())
                .mandal(mandal.getName())
                .updatedBy(person.getUpdatedBy())
                .dateOfLeaving(localDateMapper.toDate(stateHead.getDateOfLeaving()))
                .dateOfJoining(localDateMapper.toDate(stateHead.getDateOfJoining()))
                .build();
    }
}
