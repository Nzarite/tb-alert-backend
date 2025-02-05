package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.beehyv.tbalert.tbalertbackend.mapper.ContactScreeningMapper;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepo;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactScreeningServiceImpl implements ContactScreeningService {
    private final ContactScreeningRepo contactScreeningRepo;
    private final ContactScreeningMapper contactScreeningMapper;

    @Override
    public ContactScreeningOutputDTO getContactScreeningById(Integer patientId) {
        ContactScreening contactScreening = contactScreeningRepo.findByPatientId(patientId);
        return contactScreeningMapper.toContactScreeningOutputDTO(contactScreening);

    }

    @Override
    public void saveContactScreening(ContactScreeningInputDTO contactScreeningInputDTO) {
        ContactScreening contactScreening = contactScreeningMapper.toContactScreening(contactScreeningInputDTO);
        contactScreeningRepo.save(contactScreening);
    }

    @Override
    public void deleteContactScreeningById(Integer id) {
        ContactScreening contactScreening = contactScreeningRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Contact Screening id: " + id));
        contactScreeningRepo.delete(contactScreening);
    }

    @Override
    public void deleteContactScreeningByPatientId(Integer patientId) {
        ContactScreening contactScreening = contactScreeningRepo.findByPatientId(patientId);
        contactScreeningRepo.delete(contactScreening);
    }


}
