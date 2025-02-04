package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface MedicationService {
    MedicationOutputDTO add(@Valid MedicationInputDTO medication);

    List<MedicationOutputDTO> getAll();
}
