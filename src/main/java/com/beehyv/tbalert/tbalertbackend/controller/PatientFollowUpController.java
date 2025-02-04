package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/followup")
@AllArgsConstructor
public class PatientFollowUpController {

    private final PatientFollowUpService patientFollowUpService;

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientFollowUpOutputDTO>> getPatientFollowUp(@PathVariable int id) {
        try {
            log.info("Controller called for getPatientFollowUp: {}", id);
            return new ResponseEntity<>(patientFollowUpService.get(id), HttpStatus.FOUND);
        }
        catch (Exception e) {
            log.error("Controller error for getPatientFollowUp: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<PatientFollowUpOutputDTO> postPatientFollowUp(@PathVariable int id, @RequestBody @Valid PatientFollowUpInputDTO patientFollowUpInputDTO) {
        try {
            log.info("Controller called for postPatientFollowUp: {}", id);
            return new ResponseEntity<>(patientFollowUpService.add(id, patientFollowUpInputDTO), HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error("Controller error for postPatientFollowUp: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
