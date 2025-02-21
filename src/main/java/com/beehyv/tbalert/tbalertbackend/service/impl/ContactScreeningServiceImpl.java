package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.beehyv.tbalert.tbalertbackend.mapper.ContactScreeningMapper;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepository;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ContactScreeningServiceImpl implements ContactScreeningService {

    private final ContactScreeningRepository contactScreeningRepository;
    private final ContactScreeningMapper contactScreeningMapper;

    @Override
    public ContactScreeningOutputDTO getContactScreeningById(String patientId) {
        log.info("inside getContactScreeningById");

        ContactScreening contactScreening = contactScreeningRepository.findByPatient_Id(patientId).orElseThrow(()->new IllegalArgumentException("Patient with id " + patientId + " not found"));
        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);
    }

    @Override
    public void saveContactScreening(ContactScreeningInputDTO contactScreeningInputDTO) {
        log.info("inside saveContactScreening");

        ContactScreening contactScreening = contactScreeningMapper.toContactScreening(contactScreeningInputDTO);
        contactScreeningRepository.save(contactScreening);
    }

    @Override
    public void deleteContactScreeningById(Integer id) {
        log.info("inside deleteContactScreeningById");

        ContactScreening contactScreening = contactScreeningRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact Screening id: " + id));
        contactScreeningRepository.delete(contactScreening);
    }

    @Override
    public void deleteContactScreeningByPatientId(String patientId) {
        log.info("inside deleteContactScreeningByPatientId");

        ContactScreening contactScreening = contactScreeningRepository.findByPatient_Id(patientId).orElseThrow(()->new IllegalArgumentException("Patient with id " + patientId + " not found"));
        contactScreeningRepository.delete(contactScreening);
    }
}
