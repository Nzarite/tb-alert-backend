package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.StateHead;
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

        PersonOutputDTO personOutputDTO = personService.add(teleCallerInputDTO);
        TeleCaller teleCaller = new TeleCaller();
        teleCaller.setPerson(personMapper.find(personOutputDTO.getId()));
        teleCaller.setDateOfJoining(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfJoining()));
        teleCallerRepo.save(teleCaller);
        return teleCallerMapper.toTeleCallerOutputDTO(teleCaller);
    }

    @Override
    public PersonOutputDTO getByPersonId(Long id) {
        return personMapper.toPersonOutputDTO(personMapper.find(id));
    }

    @Override
    public List<TeleCallerOutputDTO> getByState(String state) {
        List<TeleCaller> teleCallers = teleCallerRepo.findByPerson_Address_StateAndPerson_IsDeletedFalse(state);
        log.info(state);
        log.info(teleCallers.toString());
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getAll() {
        return teleCallerRepo.findByPerson_IsDeletedFalse()
                .stream()
                .map(teleCallerMapper::toTeleCallerOutputDTO)
                .toList();
    }

    @Override
    public void deleteTeleCaller(Long id) {
        TeleCaller teleCaller = teleCallerMapper.find(id);

        String deleteResponse = keycloakUserService.deleteUserByEmail(teleCaller.getPerson().getEmail());
        if (deleteResponse.equals("User deleted successfully")) {
            teleCaller.getPerson().setIsDeleted(true);
            teleCaller.getPerson().setEmail(null);
            personRepo.save(teleCaller.getPerson());
            teleCallerRepo.save(teleCaller);

            log.info("TeleCaller with ID {} deleted successfully", id);
        } else {
            log.warn("TeleCaller with ID {} not found in Keycloak", id);
        }
    }
}
