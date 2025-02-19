package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.TBDetailsInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TBDetailsOutputDTO;
import jakarta.validation.Valid;

public interface TBDetailsService {

    TBDetailsOutputDTO getTBDetails(String patientId);

    TBDetailsOutputDTO registerTBDetails(@Valid TBDetailsInputDTO tbDetailsInputDTO);

    TBDetailsOutputDTO updateTBDetails(@Valid TBDetailsInputDTO tbDetailsInputDTO, String patientId);

    void deleteTBDetails(String patientId);
}
