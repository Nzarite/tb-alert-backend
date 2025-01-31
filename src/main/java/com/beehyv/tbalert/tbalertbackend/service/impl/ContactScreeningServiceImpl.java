package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dto.ContactScreeningDTO;
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
    public ContactScreeningDTO getContactScreeningById(Integer patientId) {
        ContactScreening contactScreening = contactScreeningRepository.findByPatientId(patientId);
        return contactScreeningMapper.toContactScreeningDTO(contactScreening);

    }

    @Override
    public void saveContactScreening(ContactScreeningDTO contactScreeningDTO) {
        ContactScreening contactScreening = contactScreeningMapper.toContactScreening(contactScreeningDTO);
        contactScreeningRepository.save(contactScreening);
    }
}
