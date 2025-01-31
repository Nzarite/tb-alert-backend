package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private final PatientRegistrationService patientRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody @Valid PatientInputDTO patientInputDTO) {
        return new ResponseEntity<>(patientRegistrationService.register(patientInputDTO),HttpStatus.CREATED);

    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientOutputDTO> getPatient(@PathVariable int patientId) {
        return new ResponseEntity<>(patientRegistrationService.getPatient(patientId),HttpStatus.FOUND);
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable int patientId,@RequestBody @Valid PatientInputDTO patientInputDTO) {
        patientRegistrationService.updatePatient(patientId,patientInputDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable int patientId) {
        patientRegistrationService.deletePatient(patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientOutputDTO>> getAllPatients() {
        return new ResponseEntity<>(patientRegistrationService.getAll(),HttpStatus.OK);
    }
}
