package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.DistrictInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.DistrictOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.DistrictService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/district")
@AllArgsConstructor
@Slf4j
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping("/add")
    public ResponseEntity<DistrictOutputDTO>addDistrict(@RequestBody @Valid DistrictInputDTO districtInputDTO) {
        log.info("addDistrict: {}", districtInputDTO);
        return new ResponseEntity<>(districtService.addDistrict(districtInputDTO), HttpStatus.CREATED);
    }
}
