package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followup")
@AllArgsConstructor
public class PatientFollowUpController {

    private final PatientFollowUpService patientFollowUpService;

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientFollowUpOutputDTO>> getPatientFollowUp(@PathVariable int id) {
        return new ResponseEntity<>(patientFollowUpService.get(id), HttpStatus.FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PatientFollowUpOutputDTO> postPatientFollowUp(@PathVariable int id, @RequestBody @Valid PatientFollowUpInputDTO patientFollowUpInputDTO) {
        return new ResponseEntity<>(patientFollowUpService.add(id,patientFollowUpInputDTO),HttpStatus.CREATED);
    }
}
