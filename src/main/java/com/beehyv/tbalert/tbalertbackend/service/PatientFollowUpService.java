package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PatientFollowUpService {
    List<PatientFollowUpOutputDTO> get(int id);

    PatientFollowUpOutputDTO add(int id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);

    PatientFollowUpOutputDTO update(int id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);
}
