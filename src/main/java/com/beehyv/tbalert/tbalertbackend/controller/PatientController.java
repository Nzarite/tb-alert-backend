package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientUpdateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.repository.ContactScreeningRepository;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
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
    private final ContactScreeningRepository contactScreeningRepository;
    private final ContactScreeningService contactScreeningService;

    @PostMapping("/register")
    public ResponseEntity<PatientOutputDTO> registerPatient(@RequestBody @Valid PersonInputDTO personInputDTO) {
            log.info("Controller called for Registering patient: {}", personInputDTO.toString());
            return new ResponseEntity<>(patientRegistrationService.register(personInputDTO), HttpStatus.CREATED);

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
    public ResponseEntity<HttpStatus> updatePatient(@PathVariable int patientId,@RequestBody @Valid PatientUpdateInputDTO patientUpdateInputDTO) {
            log.info("Controller being called for Updating patient: {}", patientUpdateInputDTO.toString());
            patientRegistrationService.updatePatient(patientId, patientUpdateInputDTO);
            log.info("Patient successfully updated: {}", patientUpdateInputDTO.toString());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable int patientId) {
            log.info("Controller called for deleting patient: {}", patientId);
            patientRegistrationService.deletePatient(patientId);
            log.info("Patient successfully deleted: {}", patientId);
        contactScreeningService.deleteContactScreeningByPatientId(patientId);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientOutputDTO>> getAllPatients() {
            log.info("Controller called for Getting all patients");
            return new ResponseEntity<>(patientRegistrationService.getAll(), HttpStatus.OK);
    }


}
