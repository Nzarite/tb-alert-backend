package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.TBDetailsInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TBDetailsOutputDTO;
import jakarta.validation.Valid;

public interface TBDetailsService {

    TBDetailsOutputDTO getTBDetails(Integer patientId);

    TBDetailsOutputDTO registerTBDetails(@Valid TBDetailsInputDTO tbDetailsInputDTO);

    TBDetailsOutputDTO updateTBDetails(@Valid TBDetailsInputDTO tbDetailsInputDTO, Integer patientId);

    void deleteTBDetails(Integer patientId);
}
