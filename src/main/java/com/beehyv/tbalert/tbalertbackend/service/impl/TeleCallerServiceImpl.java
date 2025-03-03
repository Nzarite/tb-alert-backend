package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.TeleCallerMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.repository.TeleCallerRepo;
import com.beehyv.tbalert.tbalertbackend.service.KeycloakUserService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
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
public class TeleCallerServiceImpl implements TeleCallerService {

    private final PersonMapper personMapper;
    private final TeleCallerRepo teleCallerRepo;
    private final PersonService personService;
    private final TeleCallerMapper teleCallerMapper;
    private final KeycloakUserService keycloakUserService;
    private final LocalDateMapper localDateMapper;
    private final PersonRepo personRepo;

    @Override
    public TeleCallerOutputDTO add(TeleCallerInputDTO teleCallerInputDTO) {

        String keycloakResponse = keycloakUserService.createUser(
                teleCallerInputDTO.getEmail(),
                "Telecaller"
        );

        if (!"User created and role assigned successfully".equals(keycloakResponse)) {
            log.error("Failed to create user in Keycloak: {}", keycloakResponse);
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        try {
            PersonOutputDTO personOutputDTO = personService.add(teleCallerInputDTO);
            TeleCaller teleCaller = new TeleCaller();
            teleCaller.setPerson(personMapper.find(personOutputDTO.getId()));
            teleCaller.setDateOfJoining(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfJoining()));
            teleCallerRepo.save(teleCaller);
            return teleCallerMapper.toTeleCallerOutputDTO(teleCaller);

        } catch (RuntimeException e) {
            log.error("Failed to save TeleCaller in database, rolling back Keycloak user creation ", e);
            keycloakUserService.deleteUserByEmail(teleCallerInputDTO.getEmail());
            throw e;
        }
    }

    @Override
    public TeleCallerOutputDTO getById(Long id) {
        return teleCallerMapper.toTeleCallerOutputDTO(teleCallerMapper.find(id));
    }

    @Override
    public List<TeleCallerOutputDTO> getByState(String state) {
        List<TeleCaller> teleCallers = teleCallerRepo.findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse(state);
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getAllNotDeleted() {
        return teleCallerRepo.findAllByPerson_IsDeletedFalse()
                .stream()
                .map(teleCallerMapper::toTeleCallerOutputDTO)
                .toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getAll() {
        return teleCallerRepo.findAll()
                .stream()
                .map(teleCallerMapper::toTeleCallerOutputDTO)
                .toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getByName(String name) {
        List<TeleCaller> teleCallers = teleCallerRepo.findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCaseAndPerson_IsDeletedFalse(name, name);
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public TeleCallerOutputDTO updateTeleCaller(Long id, TeleCallerInputDTO teleCallerInputDTO) {
        TeleCaller teleCaller = teleCallerMapper.find(id);
        if (teleCallerInputDTO.getFirstName() != null)
            teleCaller.getPerson().setFirstName(teleCallerInputDTO.getFirstName());
        if (teleCallerInputDTO.getLastName() != null)
            teleCaller.getPerson().setLastName(teleCallerInputDTO.getLastName());
        if (teleCallerInputDTO.getDateOfJoining() != null)
            teleCaller.setDateOfJoining(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfJoining()));
        if (teleCallerInputDTO.getGender() != null)
            teleCaller.getPerson().setGender(teleCallerInputDTO.getGender());
        if (teleCallerInputDTO.getPhoneNumber() != null)
            teleCaller.getPerson().setPhoneNumber(teleCallerInputDTO.getPhoneNumber());
        if (teleCallerInputDTO.getDateOfLeaving() != null)
            teleCaller.setDateOfLeaving(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfLeaving()));

        teleCaller.setPerson(teleCaller.getPerson());
        personRepo.save(teleCaller.getPerson());
        return teleCallerMapper.toTeleCallerOutputDTO(teleCallerRepo.save(teleCaller));
    }

    @Override
    public void deleteTeleCaller(Long id) {
        TeleCaller teleCaller = teleCallerMapper.find(id);

        String deleteResponse = keycloakUserService.deleteUserByEmail(teleCaller.getPerson().getEmail());
        if (deleteResponse.equals("User deleted successfully")) {
            teleCaller.getPerson().setIsDeleted(true);
            teleCaller.getPerson().setEmail(null);
            teleCaller.setDateOfLeaving(LocalDate.now());
            personRepo.save(teleCaller.getPerson());
            teleCallerRepo.save(teleCaller);

            log.info("TeleCaller with ID {} deleted successfully", id);
        } else {
            log.warn("TeleCaller with ID {} not found in Keycloak", id);
        }
    }

    @Override
    public List<TeleCallerOutputDTO> getAllByState(String state) {
        List<TeleCaller>teleCallers=teleCallerRepo.findAllByPerson_Address_State_StateName(state);
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getAllDeleted() {
        return teleCallerRepo.findAllByPerson_IsDeletedTrue().stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getDeletedByState(String state) {
        return teleCallerRepo.findAllByPerson_IsDeletedTrueAndPerson_Address_State_StateName(state).stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getTelecallerByState(String state, String name) {
        List<TeleCaller> teleCallers = teleCallerRepo.findTeleCallerByNameAndState(name,state);

        return teleCallers
                .stream()
                .map(teleCallerMapper::toTeleCallerOutputDTO)
                .toList();
    }
}
