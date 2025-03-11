package com.beehyv.tbalert.tbalertbackend.service;


import com.beehyv.tbalert.tbalertbackend.dto.input.DistrictInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.DistrictOutputDTO;
import jakarta.validation.Valid;

public interface DistrictService {

    DistrictOutputDTO addDistrict(@Valid DistrictInputDTO districtInputDTO);
}
