package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.*;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;

import java.util.List;

public interface PersonService {
    PersonOutputDTO add(PersonInputDTO person);

    PersonOutputDTO add(TeleCallerInputDTO teleCaller);

    PersonOutputDTO add(StateHeadInputDTO stateHead);

    PersonOutputDTO add(PatientInputDTO patient);

    PersonOutputDTO add(GPHeadInputDTO gpHead);

    PersonOutputDTO add(FieldCoordinatorInputDTO fieldCoordinatorInputDTO);

    PersonOutputDTO get(Long id);

    List<PersonOutputDTO> getAll();

    PersonOutputDTO getByEmail(String email);

    List<PersonOutputDTO> getByState(String state);

    int getCountOfUsersCreated(Long personId);
}
