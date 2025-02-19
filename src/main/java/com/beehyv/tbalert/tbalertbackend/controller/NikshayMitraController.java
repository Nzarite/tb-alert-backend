package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.NikshayOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.NikshayMitraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nikshaymitra")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class NikshayMitraController {

    private final NikshayMitraService nikshayMitraService;

    @GetMapping("/{patientId}")
    public ResponseEntity<NikshayOutputDTO> getNikshayMitraDetails(@PathVariable String patientId) {
        return new ResponseEntity<>(nikshayMitraService.getNikshayDetails(patientId), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<NikshayOutputDTO> registerNikshayDetails(@RequestBody @Valid NikshayInputDTO nikshayInputDTO) {
        return new ResponseEntity<>(nikshayMitraService.registerNikshayDetails(nikshayInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<NikshayOutputDTO> updateNikshayDetails(@PathVariable String patientId, @RequestBody @Valid NikshayInputDTO nikshayInputDTO) {
        return new ResponseEntity<>(nikshayMitraService.updateNikshayDetails(patientId, nikshayInputDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<HttpStatus> deleteNikshayDetails(@PathVariable String patientId) {
        nikshayMitraService.deleteNikshayDetails(patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
