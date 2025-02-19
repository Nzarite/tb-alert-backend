package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import jakarta.validation.Valid;

public interface NikshayMitraService {
    NikshayOutputDTO getNikshayDetails(String patientId);

    NikshayOutputDTO registerNikshayDetails(@Valid NikshayInputDTO nikshayInputDTO);

    NikshayOutputDTO updateNikshayDetails(String patientId, @Valid NikshayInputDTO nikshayInputDTO);

    void deleteNikshayDetails(String patientId);
}
