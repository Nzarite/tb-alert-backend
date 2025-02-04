package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.PatientMedication;
import com.beehyv.tbalert.tbalertbackend.service.PatientMedicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patientmedication")
@AllArgsConstructor
public class PatientMedicationController {

    private final PatientMedicationService patientMedicationService;

    @PostMapping("/{id}")
    public ResponseEntity<List<PatientMedicationOutputDTO>> postPatientMedication(@PathVariable int id,@RequestBody List<PatientMedicationInputDTO> patientMedicationInputDTOList) {

        try {
            log.info("Contoller for POST Patient Medication Request called for:{} ",patientMedicationInputDTOList);
            return new ResponseEntity<>(patientMedicationService.add(id, patientMedicationInputDTOList), HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientMedicationOutputDTO>> getPatientMedicationById(@PathVariable int id) {
        try {
            log.info("Controller called for GET Patient Medication Request called for:{} ",id);
            return new ResponseEntity<>(patientMedicationService.get(id), HttpStatus.FOUND);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
