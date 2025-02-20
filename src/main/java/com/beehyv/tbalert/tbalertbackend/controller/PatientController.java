package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepo;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patient")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class PatientController {

    private final PatientRegistrationService patientRegistrationService;
    private final ContactScreeningRepo contactScreeningRepo;
    private final ContactScreeningService contactScreeningService;

    @PostMapping("/register")
    public ResponseEntity<PatientOutputDTO> registerPatient(@RequestBody @Valid PatientInputDTO patientInputDTO) {
            log.info("Controller called for Registering patient: {}", patientInputDTO.toString());
            return new ResponseEntity<>(patientRegistrationService.register(patientInputDTO), HttpStatus.CREATED);

    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientOutputDTO> getPatient(@PathVariable int patientId) {
            log.info("Controller called for Getting patient: {}", patientId);
            return new ResponseEntity<>(patientRegistrationService.getPatient(patientId), HttpStatus.OK);
    }

    @GetMapping("/name/{patientName}")
    public ResponseEntity<List<PatientOutputDTO>> getPatientByName(@PathVariable String patientName) {
            log.info("Controller called for Getting patient by name: {}", patientName);
            return new ResponseEntity<>(patientRegistrationService.getPatientByName(patientName),HttpStatus.OK);

    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<HttpStatus> updatePatient(@PathVariable int patientId,@RequestBody @Valid PatientInputDTO patientInputDTO) {
            log.info("Controller being called for Updating patient: {}", patientInputDTO.toString());
            patientRegistrationService.updatePatient(patientId, patientInputDTO);
            log.info("Patient successfully updated: {}", patientInputDTO);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable int patientId) {
            log.info("Controller called for deleting patient: {}", patientId);
            contactScreeningService.deleteContactScreeningByPatientId(patientId);
            patientRegistrationService.deletePatient(patientId);
            log.info("Patient successfully deleted: {}", patientId);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientOutputDTO>> getAllPatients() {
            log.info("Controller called for Getting all patients");
            return new ResponseEntity<>(patientRegistrationService.getAll(), HttpStatus.OK);
    }
}
