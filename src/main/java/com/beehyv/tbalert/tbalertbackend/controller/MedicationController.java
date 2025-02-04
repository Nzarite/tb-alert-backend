package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.MedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Medication;
import com.beehyv.tbalert.tbalertbackend.service.MedicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medication")
@AllArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationOutputDTO> addMedication(@RequestBody @Valid MedicationInputDTO medication) {
        return new ResponseEntity<>(medicationService.add(medication), HttpStatus.ACCEPTED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<MedicationOutputDTO>> getAllMedications() {
        return new ResponseEntity<>(medicationService.getAll(),HttpStatus.FOUND);
    }


}
