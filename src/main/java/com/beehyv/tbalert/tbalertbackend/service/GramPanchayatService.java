package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;

import java.util.List;

public interface GramPanchayatService {
    GramPanchayatOutputDTO addGramPanchayat(GramPanchayatInputDTO gramPanchayatInputDTO);

    List<GramPanchayatOutputDTO> getAll();

    List<GramPanchayatOutputDTO> getAllByMandalId(Long id);
}
