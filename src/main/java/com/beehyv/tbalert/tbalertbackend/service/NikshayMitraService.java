package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import jakarta.validation.Valid;

public interface NikshayMitraService {
    NikshayOutputDTO registerNikshayDetails(@Valid NikshayInputDTO nikshayInputDTO);

    NikshayOutputDTO updateNikshayDetails(int patientId, @Valid NikshayInputDTO nikshayInputDTO);

    NikshayOutputDTO getNikshayDetails(int patientId);

    void deleteNikshayDetails(int patientId);
}
