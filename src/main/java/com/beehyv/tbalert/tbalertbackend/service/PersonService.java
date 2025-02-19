package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;

import java.util.List;

public interface PersonService {
    PersonOutputDTO add(PersonInputDTO person);

    PersonOutputDTO add(TeleCallerInputDTO teleCaller);

    PersonOutputDTO add(StateHeadInputDTO stateHead);

    PersonOutputDTO add(PatientInputDTO patient);

    PersonOutputDTO get(Long id);

    List<PersonOutputDTO> getAll();

    PersonOutputDTO getByEmail(String email);

    List<PersonOutputDTO> getByState(String state);

    int getCountOfUsersCreated(Long personId);
}
