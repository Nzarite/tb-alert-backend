package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateOutputDTO;

import java.util.List;

public interface StateService {
    StateOutputDTO add(StateInputDTO stateInputDTO);

    List<StateOutputDTO> getAllStates();

    void delete(String stateName);
}
