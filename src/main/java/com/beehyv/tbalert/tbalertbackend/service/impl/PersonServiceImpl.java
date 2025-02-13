package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.service.KeycloakUserService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepo personRepo;
    private PersonMapper personMapper;
    private final KeycloakUserService keycloakUserService;

    @Override
    public PersonOutputDTO add(PersonInputDTO person) {
        log.info("Service called for Add person: {}", person);

        String keycloakResponse = keycloakUserService.createUser(
                person.getEmail(),
                person.getRole()
        );

        if (!"User created and role assigned successfully".equals(keycloakResponse)) {
            log.error("Failed to create user in Keycloak: {}", keycloakResponse);
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        Person personSaved = personMapper.toPerson(person);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO get(Long id) {
        Person person = personMapper.find(id);
        return personMapper.toPersonOutputDTO(person);
    }

    @Override
    public List<PersonOutputDTO> getAll() {
        return personRepo.findAll().stream().map(personMapper::toPersonOutputDTO).collect(Collectors.toList());
    }
}
