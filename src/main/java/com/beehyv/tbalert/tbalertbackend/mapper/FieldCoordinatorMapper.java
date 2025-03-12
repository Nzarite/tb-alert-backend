package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.output.FieldCoordinatorOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.*;
import com.beehyv.tbalert.tbalertbackend.repository.FieldCoordinatorRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class FieldCoordinatorMapper {

    private final FieldCoordinatorRepo fieldCoordinatorRepo;
    private final LocalDateMapper localDateMapper;

    public FieldCoordinator find(Long id) {
        log.debug("Finding Field Coordinator with id {}", id);
        return fieldCoordinatorRepo.findById(id).filter(fc -> !fc.getPerson().getIsDeleted()).orElseThrow(() -> new IllegalArgumentException("Field Coordinator not found for id: " + id));
    }

    public FieldCoordinatorOutputDTO toFieldCoordinatorOutputDTO(FieldCoordinator fieldCoordinator) {
        Person person = fieldCoordinator.getPerson();
        Address address = person.getAddress();
        GramPanchayat gramPanchayat=address.getGramPanchayat();
        Mandal mandal = gramPanchayat.getMandal();
        District district=mandal.getDistrict();
        State state = district.getState();

        return FieldCoordinatorOutputDTO.builder()
                .id(fieldCoordinator.getId())
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
                .dateOfLeaving(localDateMapper.toDate(fieldCoordinator.getDateOfLeaving()))
                .dateOfJoining(localDateMapper.toDate(fieldCoordinator.getDateOfJoining()))
                .build();
    }
}
