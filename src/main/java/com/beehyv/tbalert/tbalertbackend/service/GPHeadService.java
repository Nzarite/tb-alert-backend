package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.GPHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GPHeadOutputDTO;

import java.util.List;

public interface GPHeadService {
    GPHeadOutputDTO add( GPHeadInputDTO gpHeadInputDTO);

    GPHeadOutputDTO getById(Long id);

    List<GPHeadOutputDTO> getByName(String name);

    List<GPHeadOutputDTO> getByState(String name);

    List<GPHeadOutputDTO> getGPByState(String state, String name);

    GPHeadOutputDTO updateGPHead(Long id, GPHeadInputDTO gpHeadInputDTO);

    void deleteGPHead(Long id);
}
