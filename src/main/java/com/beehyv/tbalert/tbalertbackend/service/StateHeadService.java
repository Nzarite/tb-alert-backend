package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;

import java.util.List;

public interface StateHeadService {
    StateHeadOutputDTO add(StateHeadInputDTO stateHeadInputDTO);

    StateHeadOutputDTO findById(Long id);

    List<StateHeadOutputDTO> getAllNotDeleted();

    List<StateHeadOutputDTO> getStateHeadByName(String name);

    StateHeadOutputDTO update(Long id, StateHeadInputDTO stateHeadInputDTO);

    List<StateHeadOutputDTO> getAll();

    void deleteStateHead(Long id);

    List<StateHeadOutputDTO> getAllByState(String state);

    List<StateHeadOutputDTO> getAllDeleted();

    List<StateHeadOutputDTO> getAllDeletedByState(String state);

    List<StateHeadOutputDTO> getAllNotDeletedByState(String state);
}
