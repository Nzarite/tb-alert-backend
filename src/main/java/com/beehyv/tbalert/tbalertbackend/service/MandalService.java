package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.MandalInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MandalOutputDTO;

import java.util.List;

public interface MandalService {
    MandalOutputDTO addMandal(MandalInputDTO mandalInputDTO);

    List<MandalOutputDTO> getAll();

    List<MandalOutputDTO> getAllByDistrictId(Long id);
}
