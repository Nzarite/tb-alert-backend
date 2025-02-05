package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface MissedMedicationService {
    List<MissedMedicationOutputDTO> add(int id, @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS, LocalDate date);

    List<MissedMedicationOutputDTO> get(int id);
}
