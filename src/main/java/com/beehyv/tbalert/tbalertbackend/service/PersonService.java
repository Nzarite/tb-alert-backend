package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PersonService {
    PersonOutputDTO add(@Valid PersonInputDTO person);

    PersonOutputDTO get(Long id);

    List<PersonOutputDTO> getAll();
}
