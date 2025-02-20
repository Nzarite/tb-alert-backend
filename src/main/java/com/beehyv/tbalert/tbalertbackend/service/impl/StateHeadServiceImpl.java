package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.StateHead;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.StateHeadMapper;
import com.beehyv.tbalert.tbalertbackend.repository.StateHeadRepo;
import com.beehyv.tbalert.tbalertbackend.service.KeycloakUserService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.StateHeadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StateHeadServiceImpl implements StateHeadService {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final StateHeadRepo stateHeadRepo;
    private final KeycloakUserService keycloakUserService;
    private final StateHeadMapper stateHeadMapper;
    private final LocalDateMapper localDateMapper;

    @Override
    public PersonOutputDTO add(PersonInputDTO personInputDTO) {

        String keycloakResponse = keycloakUserService.createUser(
                personInputDTO.getEmail(),
                "StateCoordinator"
        );

        if (!"User created and role assigned successfully".equals(keycloakResponse)) {
            log.error("Failed to create user in Keycloak: {}", keycloakResponse);
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        PersonOutputDTO personOutputDTO=personService.add(stateHeadInputDTO);
        StateHead stateHead=new StateHead();
        stateHead.setPerson(personMapper.find(personOutputDTO.getId()));
        stateHead.setDateOfJoining(localDateMapper.toLocalDate(stateHeadInputDTO.getDateOfJoining()));
        stateHeadRepo.save(stateHead);
        return stateHeadMapper.toStateHeadOutputDTO(stateHead);
    }

    @Override
    public PersonOutputDTO findByPersonId(Long id) {
        return personMapper.toPersonOutputDTO(personMapper.find(id));
    }

    @Override
    public List<StateHeadOutputDTO> getAll() {
        return stateHeadRepo.findAll().stream().map(stateHeadMapper::toStateHeadOutputDTO).toList();
    }
}
