package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.MandalInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MandalOutputDTO;
import jakarta.validation.Valid;

public interface MandalService {
    MandalOutputDTO addMandal(MandalInputDTO mandalInputDTO);
}
