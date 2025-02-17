package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.TeleCallerMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.repository.TeleCallerRepo;
import com.beehyv.tbalert.tbalertbackend.service.KeycloakUserService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TeleCallerServiceImpl implements TeleCallerService {

    private final PersonMapper personMapper;
    private final TeleCallerRepo teleCallerRepo;
    private final PersonService personService;
    private final TeleCallerMapper teleCallerMapper;
    private final KeycloakUserService keycloakUserService;

    @Override
    public PersonOutputDTO add(PersonInputDTO personInputDTO) {

        String keycloakResponse = keycloakUserService.createUser(
                personInputDTO.getEmail(),
                "Telecaller"
        );

        if (!"User created and role assigned successfully".equals(keycloakResponse)) {
            log.error("Failed to create user in Keycloak: {}", keycloakResponse);
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        PersonOutputDTO personOutputDTO = personService.add(personInputDTO);
        TeleCaller teleCaller = new TeleCaller();
        teleCaller.setPerson(personMapper.find(personOutputDTO.getId()));
        teleCallerRepo.save(teleCaller);
        return personOutputDTO;
    }

    @Override
    public PersonOutputDTO getByPersonId(Long id) {
        return personMapper.toPersonOutputDTO(personMapper.find(id));
    }

    @Override
    public List<TeleCallerOutputDTO> getByState(String state) {
        List<TeleCaller>teleCallers=teleCallerRepo.findByPerson_Address_State(state);
        log.info(teleCallers.toString());
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }


}
