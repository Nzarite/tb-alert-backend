package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import jakarta.validation.Valid;


public interface PatientFollowUpService {
    PatientFollowUpOutputForFrontEndDto get(int id);

    PatientFollowUpOutputDTO add(int id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);

    PatientFollowUpOutputDTO update(int id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);
}
