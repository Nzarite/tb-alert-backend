package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.output.StakeHolderInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.GPHead;
import com.beehyv.tbalert.tbalertbackend.mapper.GPHeadMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.TeleCallerMapper;
import com.beehyv.tbalert.tbalertbackend.repository.GPHeadRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.service.GPHeadService;
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
public class GPHeadServiceImpl implements GPHeadService {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final GPHeadRepo gpHeadRepo;
    private final KeycloakUserService keycloakUserService;
    private final GPHeadMapper gpHeadMapper;
    private final LocalDateMapper localDateMapper;
    private final PersonRepo personRepo;
    private final TeleCallerMapper teleCallerMapper;

    @Override
    public StakeHolderInputDTO add(com.beehyv.tbalert.tbalertbackend.dto.input.StakeHolderInputDTO gpHeadInputDTO) {
        String keycloakResponse = keycloakUserService.createUser(
                gpHeadInputDTO.getEmail(),
                "GpHead"
        );

        if (!"User created and role assigned successfully".equals(keycloakResponse)) {
            log.error("Failed to create user in Keycloak: {}", keycloakResponse);
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        try {
            PersonOutputDTO personOutputDTO = personService.add(gpHeadInputDTO);
            GPHead gpHead = new GPHead();
            gpHead.setPerson(personMapper.find(personOutputDTO.getId()));
            gpHead.setDateOfJoining(localDateMapper.toLocalDate(gpHeadInputDTO.getDateOfJoining()));
            gpHeadRepo.save(gpHead);
            return gpHeadMapper.toGPHeadOutputDTO(gpHead);

        } catch (RuntimeException e) {
            log.error("Failed to save GPHead in database, rolling back Keycloak user creation ", e);
            keycloakUserService.deleteUserByEmail(gpHeadInputDTO.getEmail());
            throw e;
        }

    }

    @Override
    public StakeHolderInputDTO getById(Long id) {
        return gpHeadMapper.toGPHeadOutputDTO(gpHeadMapper.find(id));
    }

    @Override
    public List<StakeHolderInputDTO> getByName(String name) {
        List<GPHead> gpHeads = gpHeadRepo.findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(name);
        return gpHeads.stream().map(gpHeadMapper::toGPHeadOutputDTO).toList();
    }

    @Override
    public List<StakeHolderInputDTO> getByState(String state) {
        List<GPHead> gpHeads = gpHeadRepo.findByPerson_Address_State_StateNameAndPerson_IsDeletedFalse((state));
        return gpHeads.stream().map(gpHeadMapper::toGPHeadOutputDTO).toList();
    }

    @Override
    public List<StakeHolderInputDTO> getGPByState(String state, String name) {
        List<GPHead> gpHeads = gpHeadRepo.findGPHeadByNameAndState(name, state);

        return gpHeads
                .stream()
                .map(gpHeadMapper::toGPHeadOutputDTO)
                .toList();
    }

    @Override
    public StakeHolderInputDTO updateGPHead(Long id, com.beehyv.tbalert.tbalertbackend.dto.input.StakeHolderInputDTO gpHeadInputDTO) {
        GPHead gpHead = gpHeadMapper.find(id);

        if (gpHeadInputDTO.getFirstName() != null)
            gpHead.getPerson().setFirstName(gpHeadInputDTO.getFirstName());

        if (gpHeadInputDTO.getLastName() != null)
            gpHead.getPerson().setLastName(gpHeadInputDTO.getLastName());

        if (gpHeadInputDTO.getDateOfJoining() != null)
            gpHead.setDateOfJoining(localDateMapper.toLocalDate(gpHeadInputDTO.getDateOfJoining()));

        if (gpHeadInputDTO.getGender() != null)
            gpHead.getPerson().setGender(gpHeadInputDTO.getGender());

        if (gpHeadInputDTO.getPhoneNumber() != null)
            gpHead.getPerson().setPhoneNumber(gpHeadInputDTO.getPhoneNumber());

        if (gpHeadInputDTO.getDateOfLeaving() != null)
            gpHead.setDateOfLeaving(localDateMapper.toLocalDate(gpHeadInputDTO.getDateOfLeaving()));

        gpHead.setPerson(gpHead.getPerson());
        personRepo.save(gpHead.getPerson());

        gpHead = gpHeadRepo.save(gpHead);
        return gpHeadMapper.toGPHeadOutputDTO(gpHead);
    }

    @Override
    public void deleteGPHead(Long id) {
        GPHead gpHead = gpHeadMapper.find(id);
        gpHead.getPerson().setIsDeleted(true);
        gpHead.getPerson().setEmail(null);
        gpHead.setDateOfLeaving(LocalDate.now());
        personRepo.save(gpHead.getPerson());
        gpHeadRepo.save(gpHead);
    }
}
