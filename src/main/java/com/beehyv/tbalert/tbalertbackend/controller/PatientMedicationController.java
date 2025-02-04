package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.service.PatientMedicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patientmedication")
@AllArgsConstructor
public class PatientMedicationController {

    private final PatientMedicationService patientMedicationService;

    @PostMapping("/{id}")
    public ResponseEntity<List<PatientMedicationOutputDTO>> postPatientMedication(@PathVariable int id,@RequestBody List<PatientMedicationInputDTO> patientMedicationInputDTOList) {
        return new ResponseEntity<>(patientMedicationService.add(id,patientMedicationInputDTOList), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientMedicationOutputDTO>> getPatientMedicationById(@PathVariable int id) {
        return new ResponseEntity<>(patientMedicationService.get(id), HttpStatus.FOUND);
    }


}
