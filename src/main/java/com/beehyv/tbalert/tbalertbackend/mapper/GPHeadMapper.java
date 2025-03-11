package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.GPHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.repository.GPHeadRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class GPHeadMapper {

    private final GPHeadRepo GPHeadRepo;
    private final LocalDateMapper localDateMapper;

    public GPHead find(Long id) {
        log.debug("Finding GP Head with id {}", id);
        return GPHeadRepo.findById(id).filter(gp -> !gp.getPerson().getIsDeleted()).orElseThrow(() -> new IllegalArgumentException("GP Head not found for id: " + id));
    }

    public GPHeadOutputDTO toGPHeadOutputDTO(GPHead gpHead) {
        Person person = gpHead.getPerson();
        Address address = person.getAddress();
        GramPanchayat gramPanchayat=address.getGramPanchayat();
        Mandal mandal = gramPanchayat.getMandal();
        District district=mandal.getDistrict();
        State state = district.getState();

        return GPHeadOutputDTO.builder()
                .id(gpHead.getId())
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
                .block(mandal.getName())
                .updatedBy(person.getUpdatedBy())
                .dateOfLeaving(localDateMapper.toDate(gpHead.getDateOfLeaving()))
                .dateOfJoining(localDateMapper.toDate(gpHead.getDateOfJoining()))
                .build();
    }
}
