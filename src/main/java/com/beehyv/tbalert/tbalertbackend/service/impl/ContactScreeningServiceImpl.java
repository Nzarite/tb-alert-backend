package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.beehyv.tbalert.tbalertbackend.mapper.ContactScreeningMapper;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepository;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactScreeningServiceImpl implements ContactScreeningService {
    private final ContactScreeningRepository contactScreeningRepository;
    private final ContactScreeningMapper contactScreeningMapper;

    @Override
    public ContactScreeningOutputDTO getContactScreeningById(String patientId) {
        ContactScreening contactScreening = contactScreeningRepository.findByPatient_Id(patientId).orElseThrow(()->new RuntimeException("Could not find contact screening"));
        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);

    }

    @Override
    public void saveContactScreening(ContactScreeningInputDTO contactScreeningInputDTO) {
        ContactScreening contactScreening = contactScreeningMapper.toContactScreening(contactScreeningInputDTO);
        contactScreeningRepository.save(contactScreening);
    }

    @Override
    public void deleteContactScreeningById(Integer id) {
        ContactScreening contactScreening = contactScreeningRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact Screening id: " + id));
        contactScreeningRepository.delete(contactScreening);
    }

    @Override
    public void deleteContactScreeningByPatientId(String patientId) {
        ContactScreening contactScreening = contactScreeningRepository.findByPatient_Id(patientId).orElseThrow(()->new RuntimeException("Could not find contact screening"));
        contactScreeningRepository.delete(contactScreening);
    }


}
