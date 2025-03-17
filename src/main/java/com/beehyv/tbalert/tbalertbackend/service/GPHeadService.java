package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.output.StakeHolderInputDTO;

import java.util.List;

public interface GPHeadService {
    StakeHolderInputDTO add(com.beehyv.tbalert.tbalertbackend.dto.input.StakeHolderInputDTO gpHeadInputDTO);

    StakeHolderInputDTO getById(Long id);

    List<StakeHolderInputDTO> getByName(String name);

    List<StakeHolderInputDTO> getByState(String name);

    List<StakeHolderInputDTO> getGPByState(String state, String name);

    StakeHolderInputDTO updateGPHead(Long id, com.beehyv.tbalert.tbalertbackend.dto.input.StakeHolderInputDTO gpHeadInputDTO);

    void deleteGPHead(Long id);
}
