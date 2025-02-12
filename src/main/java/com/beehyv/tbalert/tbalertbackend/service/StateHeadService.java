package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import jakarta.validation.Valid;

public interface StateHeadService {
    PersonOutputDTO add(@Valid PersonInputDTO personInputDTO);

    PersonOutputDTO findByPersonId(Long id);
}
