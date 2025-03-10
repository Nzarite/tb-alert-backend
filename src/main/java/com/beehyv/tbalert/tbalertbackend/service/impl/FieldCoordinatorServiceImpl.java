package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.FieldCoordinatorInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.FieldCoordinatorOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.FieldCoordinator;
import com.beehyv.tbalert.tbalertbackend.mapper.FieldCoordinatorMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.FieldCoordinatorRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.service.FieldCoordinatorService;
import com.beehyv.tbalert.tbalertbackend.service.KeycloakUserService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class FieldCoordinatorServiceImpl implements FieldCoordinatorService {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final KeycloakUserService keycloakUserService;
    private final LocalDateMapper localDateMapper;
    private final PersonRepo personRepo;
    private final FieldCoordinatorRepo fieldCoordinatorRepo;
    private final FieldCoordinatorMapper fieldCoordinatorMapper;

    @Override
    public FieldCoordinatorOutputDTO add(FieldCoordinatorInputDTO fieldCoordinatorInputDTO) {
        String keycloakResponse = keycloakUserService.createUser(
                fieldCoordinatorInputDTO.getEmail(),
                "FieldCoordinator"
        );

        if (!"User created and role assigned successfully".equals(keycloakResponse)) {
            log.error("Failed to create user in Keycloak: {}", keycloakResponse);
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        try {
            PersonOutputDTO personOutputDTO = personService.add(fieldCoordinatorInputDTO);
            FieldCoordinator fieldCoordinator = new FieldCoordinator();
            fieldCoordinator.setPerson(personMapper.find(personOutputDTO.getId()));
            fieldCoordinator.setDateOfJoining(localDateMapper.toLocalDate(fieldCoordinatorInputDTO.getDateOfJoining()));
            fieldCoordinatorRepo.save(fieldCoordinator);
            return fieldCoordinatorMapper.toFieldCoordinatorOutputDTO(fieldCoordinator);

        } catch (RuntimeException e) {
            log.error("Failed to save FieldCoordinator in database, rolling back Keycloak user creation ", e);
            keycloakUserService.deleteUserByEmail(fieldCoordinatorInputDTO.getEmail());
            throw e;
        }

    }

    @Override
    public FieldCoordinatorOutputDTO getById(Long id) {
        return fieldCoordinatorMapper.toFieldCoordinatorOutputDTO(fieldCoordinatorMapper.find(id));
    }

    @Override
    public List<FieldCoordinatorOutputDTO> getByName(String name) {
        List<FieldCoordinator> fieldCoordinators = fieldCoordinatorRepo.findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(name);
        return fieldCoordinators.stream().map(fieldCoordinatorMapper::toFieldCoordinatorOutputDTO).toList();
    }

    @Override
    public List<FieldCoordinatorOutputDTO> getByState(String state) {
        List<FieldCoordinator> fieldCoordinators = fieldCoordinatorRepo.findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(state);
        return fieldCoordinators.stream().map(fieldCoordinatorMapper::toFieldCoordinatorOutputDTO).toList();
    }

    @Override
    public List<FieldCoordinatorOutputDTO> getFieldCoordinatorByState(String state, String name) {
        List<FieldCoordinator> fieldCoordinators = fieldCoordinatorRepo.findFieldCoordinatorByNameAndState(name, state);

        return fieldCoordinators
                .stream()
                .map(fieldCoordinatorMapper::toFieldCoordinatorOutputDTO)
                .toList();
    }

    @Override
    public FieldCoordinatorOutputDTO updateFieldCoordinator(Long id, FieldCoordinatorInputDTO fieldCoordinatorInputDTO) {
        FieldCoordinator fieldCoordinator = fieldCoordinatorMapper.find(id);

        if (fieldCoordinatorInputDTO.getFirstName() != null)
            fieldCoordinator.getPerson().setFirstName(fieldCoordinatorInputDTO.getFirstName());

        if (fieldCoordinatorInputDTO.getLastName() != null)
            fieldCoordinator.getPerson().setLastName(fieldCoordinatorInputDTO.getLastName());

        if (fieldCoordinatorInputDTO.getDateOfJoining() != null)
            fieldCoordinator.setDateOfJoining(localDateMapper.toLocalDate(fieldCoordinatorInputDTO.getDateOfJoining()));

        if (fieldCoordinatorInputDTO.getGender() != null)
            fieldCoordinator.getPerson().setGender(fieldCoordinatorInputDTO.getGender());

        if (fieldCoordinatorInputDTO.getPhoneNumber() != null)
            fieldCoordinator.getPerson().setPhoneNumber(fieldCoordinatorInputDTO.getPhoneNumber());

        if (fieldCoordinatorInputDTO.getDateOfLeaving() != null)
            fieldCoordinator.setDateOfLeaving(localDateMapper.toLocalDate(fieldCoordinatorInputDTO.getDateOfLeaving()));

        fieldCoordinator.setPerson(fieldCoordinator.getPerson());
        personRepo.save(fieldCoordinator.getPerson());

        fieldCoordinator = fieldCoordinatorRepo.save(fieldCoordinator);
        return fieldCoordinatorMapper.toFieldCoordinatorOutputDTO(fieldCoordinator);
    }

    @Override
    public void deleteFieldCoordinator(Long id) {
        FieldCoordinator fieldCoordinator = fieldCoordinatorMapper.find(id);
        fieldCoordinator.getPerson().setIsDeleted(true);
        fieldCoordinator.getPerson().setEmail(null);
        fieldCoordinator.setDateOfLeaving(LocalDate.now());
        personRepo.save(fieldCoordinator.getPerson());
        fieldCoordinatorRepo.save(fieldCoordinator);
    }
}
