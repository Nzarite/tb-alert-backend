package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.TeleCaller;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.TeleCallerMapper;
import com.beehyv.tbalert.tbalertbackend.repository.TeleCallerRepo;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeleCallerServiceImpl implements TeleCallerService {

    private final PersonMapper personMapper;
    private final TeleCallerRepo teleCallerRepo;
    private final PersonService personService;
    private final TeleCallerMapper teleCallerMapper;

    @Override
    public PersonOutputDTO add(PersonInputDTO personInputDTO) {

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
    public List<PersonOutputDTO> getByState(String name) {
        List<TeleCaller>teleCallers=teleCallerRepo.findByTeleCallerByState(name);
        log.info(teleCallers.toString());
        return teleCallers.stream().map((teleCaller -> personMapper.toPersonOutputDTO(teleCaller.getPerson()))).toList();
    }


}
