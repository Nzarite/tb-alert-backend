package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.DistrictInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.DistrictOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.DistrictService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<DistrictOutputDTO>> getAllDistricts() {
        log.info("getAllDistricts");
        return new ResponseEntity<>(districtService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<DistrictOutputDTO>> getAllDistrictByState(@PathVariable Long id) {
        log.info("getAllDistrictByState: {}", id);
        return new ResponseEntity<>(districtService.getAllByState(id),HttpStatus.OK);
    }

}
