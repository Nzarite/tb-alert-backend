package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import jakarta.validation.Valid;

public interface ContactScreeningService {
    ContactScreeningOutputDTO getContactScreeningById(Integer patientId);

    void saveContactScreening(@Valid ContactScreeningInputDTO contactScreeningInputDTO);

    void deleteContactScreeningByPatientId(Integer patientId);

    void deleteContactScreeningById(Integer id);


}
