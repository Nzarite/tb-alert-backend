package com.beehyv.tbalert.tbalertbackend.service;

import com.beehyv.tbalert.tbalertbackend.dto.input.FieldCoordinatorInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.FieldCoordinatorOutputDTO;

import java.util.List;

public interface FieldCoordinatorService {
    FieldCoordinatorOutputDTO add(FieldCoordinatorInputDTO fieldCoordinatorInputDTO);

    FieldCoordinatorOutputDTO getById(Long id);

    List<FieldCoordinatorOutputDTO> getByName(String name);

    List<FieldCoordinatorOutputDTO> getByState(String name);

    List<FieldCoordinatorOutputDTO> getFieldCoordinatorByState(String state, String name);

    FieldCoordinatorOutputDTO updateFieldCoordinator(Long id, FieldCoordinatorInputDTO fieldCoordinatorInputDTO);

    void deleteFieldCoordinator(Long id);
}
