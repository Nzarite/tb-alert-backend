package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.GPHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GPHeadOutputDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface GPHeadService {
    GPHeadOutputDTO add(@Valid GPHeadInputDTO gpHeadInputDTO);

    GPHeadOutputDTO getById(Long id);

    List<GPHeadOutputDTO> getByName(String name);

    GPHeadOutputDTO updateGPHead(Long id, GPHeadInputDTO gpHeadInputDTO);

    void deleteGPHead(Long id);
}
