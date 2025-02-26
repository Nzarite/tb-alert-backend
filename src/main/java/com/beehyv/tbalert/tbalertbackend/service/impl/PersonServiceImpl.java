package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
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
        if(personRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }


    @Override
    public PersonOutputDTO add(PersonInputDTO person) {
        log.info("Service called for Add person using person input: {}", person);
checkForEmail(person.getEmail());
        Person personSaved = personMapper.toPerson(person);
        Address address = addressMapper.toAddress(person);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(TeleCallerInputDTO teleCaller) {
        log.info("Service called for Add personusing telecaller input: {}", teleCaller);
checkForEmail(teleCaller.getEmail());
        Person personSaved = personMapper.toPerson(teleCaller);
        Address address = addressMapper.toAddress(teleCaller);
        personSaved.setAddress(address);
        personSaved = personRepo.save(personSaved);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO add(StateHeadInputDTO stateHead) {
        log.info("Service called for Add personusing statehead input: {}", stateHead);
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
        Person person = personMapper.find(personId);
        return personRepo.countByCreatedByAndIsDeletedFalse(person.getEmail());
    }
}
