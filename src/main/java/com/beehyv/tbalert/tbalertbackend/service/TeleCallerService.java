package com.beehyv.tbalert.tbalertbackend.service;


import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface TeleCallerService {

    PersonOutputDTO add(@Valid PersonInputDTO personInputDTO);

    PersonOutputDTO getByPersonId(Long id);

    List<PersonOutputDTO> getByState(String name);
}
