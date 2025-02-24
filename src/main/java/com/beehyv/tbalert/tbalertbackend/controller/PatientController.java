package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
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
@RequestMapping("/patient")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_Telecaller')")
public class PatientController {

    private final PatientRegistrationService patientRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<PatientOutputDTO> registerPatient(@RequestBody @Valid PatientInputDTO patientInputDTO) {
        log.info("Controller called for Registering patient: {}", patientInputDTO.toString());
        return new ResponseEntity<>(patientRegistrationService.register(patientInputDTO), HttpStatus.CREATED);

    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientOutputDTO> getPatient(@PathVariable String patientId) {
        log.info("Controller called for Getting patient: {}", patientId);
        return new ResponseEntity<>(patientRegistrationService.getPatient(patientId), HttpStatus.OK);
    }

    @GetMapping("/name/{patientName}")
    public ResponseEntity<List<PatientOutputDTO>> getPatientByNameOrNikshayIdOrPatientId(@PathVariable String patientName) {
        log.info("Controller called for Getting patient by name or nikshayId or patientID: {}", patientName);
        return new ResponseEntity<>(patientRegistrationService.getPatientByNameOrNikshayIdOrPatientId(patientName), HttpStatus.OK);

    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<HttpStatus> updatePatient(@PathVariable String patientId, @RequestBody @Valid PatientUpdateInputDTO patientUpdateInputDTO) {
        log.info("Controller being called for Updating patient: {}", patientUpdateInputDTO.toString());
        patientRegistrationService.updatePatient(patientId, patientUpdateInputDTO);
        log.info("Patient successfully updated: {}", patientUpdateInputDTO.toString());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable String patientId) {
        log.info("Controller called for deleting patient: {}", patientId);
        patientRegistrationService.deletePatient(patientId);
        log.info("Patient successfully deleted: {}", patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientOutputDTO>> getAllPatients() {
        log.info("Controller called for Getting all patients");
        return new ResponseEntity<>(patientRegistrationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<PatientOutputDTO>> getPatientByState(@PathVariable String state) {
        log.info("Controller called for Getting patient by state: {}", state);

        return new ResponseEntity<>(patientRegistrationService.getAllByState(state), HttpStatus.OK);
    }
}
