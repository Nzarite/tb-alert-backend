package com.beehyv.tbalert.tbalertbackend.mapper;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.State;
import com.beehyv.tbalert.tbalertbackend.repository.StateRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@AllArgsConstructor
public class StateMapper {

    private final StateRepo stateRepo;

    public State getStateByName(String stateName) {
        return stateRepo.findByStateName(stateName)
                .orElseThrow(() -> new IllegalArgumentException("State not found: " + stateName));
    }

    public State toState(StateInputDTO stateInputDTO){
        return State.builder()
                .stateName(stateInputDTO.getStateName())
                .stateCode(stateInputDTO.getStateCode())
                .build();
    }

    public StateOutputDTO toStateOutputDTO(State state){
        return StateOutputDTO.builder()
                .id(state.getId())
                .stateName(state.getStateName())
                .stateCode(state.getStateCode())
                .build();
    }
}
