package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;

import java.util.List;

public interface StateHeadService {
    StateHeadOutputDTO add(StateHeadInputDTO stateHeadInputDTO);

    StateHeadOutputDTO findById(Long id);

    List<StateHeadOutputDTO> getAll();

    List<StateHeadOutputDTO> getStateHeadByName(String name);

    StateHeadOutputDTO update(Long id, StateHeadInputDTO stateHeadInputDTO);

    void deleteStateHead(Long id);
}
