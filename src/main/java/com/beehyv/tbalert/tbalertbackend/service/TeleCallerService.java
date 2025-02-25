package com.beehyv.tbalert.tbalertbackend.service;


import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;

import java.util.List;

public interface TeleCallerService {

    TeleCallerOutputDTO add(TeleCallerInputDTO teleCallerInputDTO);

    TeleCallerOutputDTO getById(Long id);

    List<TeleCallerOutputDTO> getByState(String name);

    List<TeleCallerOutputDTO> getAll();

    List<TeleCallerOutputDTO> getByName(String name);

    TeleCallerOutputDTO updateTeleCaller(Long id, TeleCallerInputDTO teleCallerInputDTO);
}
