package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.State;
import com.beehyv.tbalert.tbalertbackend.mapper.StateMapper;
import com.beehyv.tbalert.tbalertbackend.repository.StateRepo;
import com.beehyv.tbalert.tbalertbackend.service.StateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateMapper stateMapper;
    private final StateRepo stateRepo;

    @Override
    public StateOutputDTO add(StateInputDTO stateInputDTO) {
        log.info("Service called for add state");

        State state = stateMapper.toState(stateInputDTO);
        state = stateRepo.save(state);
        return stateMapper.toStateOutputDTO(state);
    }

    @Override
    public List<StateOutputDTO> getAllStates() {
        log.info("Service called for getAllStates");

        List<State> states = stateRepo.findAll();
        return states.stream()
                .map(stateMapper::toStateOutputDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String stateName) {
        log.info("Service called for delete state");

        stateRepo.delete(stateMapper.getStateByName(stateName));
    }
}
