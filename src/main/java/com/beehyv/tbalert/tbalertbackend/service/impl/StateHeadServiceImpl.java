package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.StateHead;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.StateHeadRepo;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.StateHeadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class StateHeadServiceImpl implements StateHeadService {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final StateHeadRepo stateHeadRepo;

    @Override
    public PersonOutputDTO add(PersonInputDTO personInputDTO) {

        PersonOutputDTO personOutputDTO=personService.add(personInputDTO);
        StateHead stateHead=new StateHead();
        stateHead.setPerson(personMapper.find(personOutputDTO.getId()));
        stateHeadRepo.save(stateHead);
        return personOutputDTO;
    }

    @Override
    public PersonOutputDTO findByPersonId(Long id) {
        return personMapper.toPersonOutputDTO(personMapper.find(id));
    }
}
