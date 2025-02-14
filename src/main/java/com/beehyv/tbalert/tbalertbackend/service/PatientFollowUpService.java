package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;


public interface PatientFollowUpService {
    PatientFollowUpOutputForFrontEndDto get(int id);

    PatientFollowUpOutputDTO add(int id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);

    PatientFollowUpOutputDTO update(int id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);

    List<PatientFollowUp> findBeforeDate(int id, LocalDate localDate );
}
