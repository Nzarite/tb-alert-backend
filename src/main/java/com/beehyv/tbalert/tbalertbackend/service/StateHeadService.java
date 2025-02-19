package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;

import java.util.List;

public interface StateHeadService {
    StateHeadOutputDTO add(StateHeadInputDTO stateHeadInputDTO);

    PersonOutputDTO findByPersonId(Long id);

    List<StateHeadOutputDTO> getAll();
}
