package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import jakarta.validation.Valid;

public interface ContactScreeningService {
    ContactScreeningOutputDTO getContactScreeningById(String patientId);

    ContactScreeningOutputDTO setContactScreening(String patientId, @Valid ContactScreeningInputDTO contactScreeningInputDTO);

    ContactScreeningOutputDTO updateContactScreening(String patientId, @Valid ContactScreeningInputDTO contactScreeningInputDTO);

    void deleteContactScreeningByPatientId(String patientId);

    void deleteContactScreeningById(Integer id);
}
