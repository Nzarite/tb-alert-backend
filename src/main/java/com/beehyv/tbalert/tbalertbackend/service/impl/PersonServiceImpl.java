package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.*;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.mapper.AddressMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {

    private final AddressMapper addressMapper;
    private PersonRepo personRepo;
    private PersonMapper personMapper;

    private void checkForEmail(String email) {
        if (!ObjectUtils.isEmpty(email) && personRepo.existsByEmailAndIsDeletedFalse(email)) {
            throw new RuntimeException("Email already exists");
        }
    }

    @Override
    public PersonOutputDTO add(PersonInputDTO person) {
        log.info("Service called for Add person: {}", person);

        Person personSaved = personMapper.toPerson(person);
        Address address = addressMapper.toAddress(person);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(TeleCallerInputDTO teleCaller) {
        log.info("Service called for Add person using telecaller input: {}", teleCaller);

        checkForEmail(teleCaller.getEmail());
        Person personSaved = personMapper.toPerson(teleCaller);
        Address address = addressMapper.toAddress(teleCaller);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(StateHeadInputDTO stateHead) {
        log.info("Service called for Add person using statehead input: {}", stateHead);

        checkForEmail(stateHead.getEmail());
        Person personSaved = personMapper.toPerson(stateHead);
        Address address = addressMapper.toAddress(stateHead);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(PatientInputDTO patientInputDTO) {
        log.info("Service called for Add person using patient input: {}", patientInputDTO);

        checkForEmail(patientInputDTO.getEmail());
        Person personSaved = personMapper.toPerson(patientInputDTO);
        Address address = addressMapper.toAddress(patientInputDTO);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(GPHeadInputDTO gpHead) {
        log.info("Service called for Add person using gpHead input: {}", gpHead);

        checkForEmail(gpHead.getEmail());
        Person personSaved = personMapper.toPerson(gpHead);
        Address address = addressMapper.toAddress(gpHead);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(FieldCoordinatorInputDTO fieldCoordinatorInputDTO) {
        log.info("Service called for Add person using field coordinator input: {}", fieldCoordinatorInputDTO);

        checkForEmail(fieldCoordinatorInputDTO.getEmail());
        Person personSaved = personMapper.toPerson(fieldCoordinatorInputDTO);
        Address address = addressMapper.toAddress(fieldCoordinatorInputDTO);
        personSaved.setAddress(address);
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
        return personRepo.findAllByIsDeletedFalse().stream().map(personMapper::toPersonOutputDTO).toList();
    }

    @Override
    public PersonOutputDTO getByEmail(String email) {

        Optional<Person> person = personRepo.findByEmailAndIsDeletedFalse(email);
        if (person.isEmpty()) {
            throw new IllegalArgumentException("Person not found for email: " + email);
        }
        return personMapper.toPersonOutputDTO(person.get());
    }

    @Override
    public List<PersonOutputDTO> getByState(String state) {

        List<Person> personList = personRepo.findByAddress_State_StateNameAndIsDeletedFalse(state);
        return personList.stream().map(personMapper::toPersonOutputDTO).toList();
    }

    @Override
    public int getCountOfUsersCreated(Long personId) {
        Person person = personMapper.findEverything(personId);
        return personRepo.countByCreatedBy(person.getEmail());
    }
}
