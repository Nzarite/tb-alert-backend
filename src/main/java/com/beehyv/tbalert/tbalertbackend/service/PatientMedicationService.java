package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;

import java.util.List;

public interface PatientMedicationService {
    List<PatientMedicationOutputDTO> get(int id);

    List<PatientMedicationOutputDTO> add(int id, List<PatientMedicationInputDTO> patientMedicationInputDTOList);
}
