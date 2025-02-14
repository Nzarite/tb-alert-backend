package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.MedicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/medication")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_Telecaller')")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationOutputDTO> addMedication(@RequestBody @Valid MedicationInputDTO medication) {
            log.info("Controller for Add medication called for {}", medication);
            return new ResponseEntity<>(medicationService.add(medication), HttpStatus.ACCEPTED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<MedicationOutputDTO>> getAllMedications() {
            log.info("Controller for Get All medications called");
            return new ResponseEntity<>(medicationService.getAll(), HttpStatus.OK);
    }


}
