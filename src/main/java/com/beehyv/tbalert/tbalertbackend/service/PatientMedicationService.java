package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;

import java.util.List;

public interface PatientMedicationService {
    List<PatientMedicationOutputDTO> get(String id);

    List<PatientMedicationOutputDTO> add(String id, List<PatientMedicationInputDTO> patientMedicationInputDTOList);
}
