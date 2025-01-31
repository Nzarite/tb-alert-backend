package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.NikshayMitraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class NikshayMitraController {

    private final NikshayMitraService nikshayMitraService;

    @PostMapping("/nikshaymitra")
    public ResponseEntity<?> registerNikshayDetails(@RequestBody @Valid NikshayInputDTO nikshayInputDTO) {
        return new ResponseEntity<>(nikshayMitraService.registerNikshayDetails(nikshayInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/nikshaymitra/{patientId}")
    public ResponseEntity<?> updateNikshayDetails(@PathVariable int patientId, @RequestBody @Valid NikshayInputDTO nikshayInputDTO) {
        return new ResponseEntity<>(nikshayMitraService.updateNikshayDetails(patientId, nikshayInputDTO), HttpStatus.ACCEPTED);
    }
}
