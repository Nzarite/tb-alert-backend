package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.ContactScreeningDTO;

public interface ContactScreeningService {
    ContactScreeningDTO getContactScreeningById(Integer patientId);
    void saveContactScreening(ContactScreeningDTO contactScreeningDTO);
}
