package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Address;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.mapper.AddressMapper;
import com.beehyv.tbalert.tbalertbackend.mapper.PersonMapper;
import com.beehyv.tbalert.tbalertbackend.repository.AddressRepo;
import com.beehyv.tbalert.tbalertbackend.repository.PersonRepo;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final AddressMapper addressMapper;
    private final AddressRepo addressRepo;
    private PersonRepo personRepo;
    private PersonMapper personMapper;

    @Override
    public PersonOutputDTO add(PersonInputDTO person) {
        log.info("Service called for Add person: {}", person);
        Person personSaved = personMapper.toPerson(person);
        personSaved = personRepo.save(personSaved);
        Address address = addressMapper.toAddress(personSaved, person);
        addressRepo.save(address);
        return personMapper.toPersonOutputDTO(personSaved);
    }

    @Override
    public PersonOutputDTO get(Long id) {
        Person person = personMapper.find(id);
        return personMapper.toPersonOutputDTO(person);
    }

    @Override
    public List<PersonOutputDTO> getAll() {
        return personRepo.findAll().stream().map(personMapper::toPersonOutputDTO).toList();
    }

    @Override
    public PersonOutputDTO getByEmail(String email) {

        Person person=personRepo.findByEmail(email);
        if(person==null){
            throw new IllegalArgumentException("Person not found for email: "+email);
        }
        return personMapper.toPersonOutputDTO(person);
    }

    @Override
    public List<PersonOutputDTO> getByState(String state) {

        List<Person>personList=personRepo.findByState(state);
        return personList.stream().map(personMapper::toPersonOutputDTO).toList();
    }
}
