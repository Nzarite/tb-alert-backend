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
        List<FieldCoordinator> gpHeads = fieldCoordinatorRepo.findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(name);
        return gpHeads.stream().map(fieldCoordinatorMapper::toFieldCoordinatorOutputDTO).toList();
    }

    @Override
    public List<FieldCoordinatorOutputDTO> getByState(String state) {
        List<FieldCoordinator> fieldCoordinators = fieldCoordinatorRepo.findByPerson_Address_GramPanchayat_Mandal_District_State_StateNameAndPerson_IsDeletedFalse(state);
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
        FieldCoordinator gpHead = fieldCoordinatorMapper.find(id);

        if (fieldCoordinatorInputDTO.getFirstName() != null)
            gpHead.getPerson().setFirstName(fieldCoordinatorInputDTO.getFirstName());

        if (fieldCoordinatorInputDTO.getLastName() != null)
            gpHead.getPerson().setLastName(fieldCoordinatorInputDTO.getLastName());

        if (fieldCoordinatorInputDTO.getDateOfJoining() != null)
            gpHead.setDateOfJoining(localDateMapper.toLocalDate(fieldCoordinatorInputDTO.getDateOfJoining()));

        if (fieldCoordinatorInputDTO.getGender() != null)
            gpHead.getPerson().setGender(fieldCoordinatorInputDTO.getGender());

        if (fieldCoordinatorInputDTO.getPhoneNumber() != null)
            gpHead.getPerson().setPhoneNumber(fieldCoordinatorInputDTO.getPhoneNumber());

        if (fieldCoordinatorInputDTO.getDateOfLeaving() != null)
            gpHead.setDateOfLeaving(localDateMapper.toLocalDate(fieldCoordinatorInputDTO.getDateOfLeaving()));

        gpHead.setPerson(gpHead.getPerson());
        personRepo.save(gpHead.getPerson());

        gpHead = fieldCoordinatorRepo.save(gpHead);
        return fieldCoordinatorMapper.toFieldCoordinatorOutputDTO(gpHead);
    }

    @Override
    public void deleteFieldCoordinator(Long id) {
        FieldCoordinator gpHead = fieldCoordinatorMapper.find(id);
        gpHead.getPerson().setIsDeleted(true);
        gpHead.getPerson().setEmail(null);
        gpHead.setDateOfLeaving(LocalDate.now());
        personRepo.save(gpHead.getPerson());
        fieldCoordinatorRepo.save(gpHead);
    }
}
