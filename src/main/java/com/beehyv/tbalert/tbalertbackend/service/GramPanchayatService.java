package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;
import jakarta.validation.Valid;

public interface GramPanchayatService {
    GramPanchayatOutputDTO addGramPanchayat(GramPanchayatInputDTO gramPanchayatInputDTO);
}
