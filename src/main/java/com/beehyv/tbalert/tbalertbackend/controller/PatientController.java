package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private final PatientRegistrationService patientRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody @Valid PatientInputDTO patientInputDTO) {
        try {
            log.info("Controller called for Registering patient: {}", patientInputDTO.toString());
            return new ResponseEntity<>(patientRegistrationService.register(patientInputDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientOutputDTO> getPatient(@PathVariable int patientId) {
        try {
            log.info("Controller called for Getting patient: {}", patientId);
            return new ResponseEntity<>(patientRegistrationService.getPatient(patientId), HttpStatus.FOUND);
        }
        catch (Exception e) {
            log.info("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{patientName}")
    public ResponseEntity<List<PatientOutputDTO>> getPatientByName(@PathVariable String patientName) {
        try{
            log.info("Controller called for Getting patient by name: {}", patientName);
            return new ResponseEntity<List<PatientOutputDTO>>(patientRegistrationService.getPatientByName(patientName),HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.info("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable int patientId,@RequestBody @Valid PatientInputDTO patientInputDTO) {
        try {
            log.info("Controller being called for Updating patient: {}", patientInputDTO.toString());
            patientRegistrationService.updatePatient(patientId, patientInputDTO);
            log.info("Patient successfully updated: {}", patientInputDTO);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            log.info("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable int patientId) {
        try {
            log.info("Controller called for deleting patient: {}", patientId);
            patientRegistrationService.deletePatient(patientId);
            log.info("Patient successfully deleted: {}", patientId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            log.info("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientOutputDTO>> getAllPatients() {
        try {
            log.info("Controller called for Getting all patients");
            return new ResponseEntity<>(patientRegistrationService.getAll(), HttpStatus.OK);
        }
        catch (Exception e) {
            log.info("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
