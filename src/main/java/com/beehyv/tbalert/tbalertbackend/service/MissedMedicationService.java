package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface MissedMedicationService {
    List<MissedMedicationOutputDTO> add(String id, @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS, LocalDate date);

    List<MissedMedicationOutputDTO> get(String id);

    List<MissedMedicationOutputDTO> findByDate(LocalDate date, Patient patient);
}
