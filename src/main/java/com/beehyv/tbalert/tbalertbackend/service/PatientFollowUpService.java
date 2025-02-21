package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.PatientFollowUp;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;


public interface PatientFollowUpService {
    PatientFollowUpOutputForFrontEndDto get(String id);

    PatientFollowUpOutputDTO add(String id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);

    PatientFollowUpOutputDTO update(String id, @Valid PatientFollowUpInputDTO patientFollowUpInputDTO);

    List<PatientFollowUp> findBeforeDate(String id, LocalDate localDate );

    List<PatientFollowUpOutputForFrontEndDto> getFollowUpForPatientList(List<PatientOutputDTO>patientList);
}
