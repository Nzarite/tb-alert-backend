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
    private final LocalDateMapper localDateMapper;
    private final PersonRepo personRepo;

    @Override
    public TeleCallerOutputDTO add(TeleCallerInputDTO teleCallerInputDTO) {

        PersonOutputDTO personOutputDTO = personService.add(teleCallerInputDTO);
        TeleCaller teleCaller = new TeleCaller();
        teleCaller.setPerson(personMapper.find(personOutputDTO.getId()));
        teleCaller.setDateOfJoining(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfJoining()));
        teleCallerRepo.save(teleCaller);
        return teleCallerMapper.toTeleCallerOutputDTO(teleCaller);
    }

    @Override
    public TeleCallerOutputDTO getById(Long id) {
        return teleCallerMapper.toTeleCallerOutputDTO(teleCallerMapper.find(id));
    }

    @Override
    public List<TeleCallerOutputDTO> getByState(String state) {
        List<TeleCaller>teleCallers=teleCallerRepo.findByPerson_Address_State(state);
        log.info(state);
        log.info(teleCallers.toString());
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getAll() {
        return teleCallerRepo.findAll().stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public List<TeleCallerOutputDTO> getByName(String name) {
        List<TeleCaller>teleCallers=teleCallerRepo.findAllByPerson_FirstNameContainingIgnoreCaseOrPerson_LastNameContainingIgnoreCase(name,name);
        return teleCallers.stream().map(teleCallerMapper::toTeleCallerOutputDTO).toList();
    }

    @Override
    public TeleCallerOutputDTO updateTeleCaller(Long id, TeleCallerInputDTO teleCallerInputDTO) {
        TeleCaller teleCaller=teleCallerMapper.find(id);
        if(teleCallerInputDTO.getFirstName()!=null)
            teleCaller.getPerson().setFirstName(teleCallerInputDTO.getFirstName());
        if(teleCallerInputDTO.getLastName()!=null)
            teleCaller.getPerson().setLastName(teleCallerInputDTO.getLastName());
        if(teleCallerInputDTO.getDateOfJoining()!=null)
            teleCaller.setDateOfJoining(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfJoining()));
        if(teleCallerInputDTO.getGender()!=null)
            teleCaller.getPerson().setGender(teleCallerInputDTO.getGender());
        if(teleCallerInputDTO.getPhoneNumber()!=null)
            teleCaller.getPerson().setPhoneNumber(teleCallerInputDTO.getPhoneNumber());
        if(teleCallerInputDTO.getDateOfLeaving()!=null)
            teleCaller.setDateOfLeaving(localDateMapper.toLocalDate(teleCallerInputDTO.getDateOfLeaving()));

        teleCaller.setPerson(teleCaller.getPerson());
        personRepo.save(teleCaller.getPerson());
        return teleCallerMapper.toTeleCallerOutputDTO(teleCallerRepo.save(teleCaller));
    }


}
