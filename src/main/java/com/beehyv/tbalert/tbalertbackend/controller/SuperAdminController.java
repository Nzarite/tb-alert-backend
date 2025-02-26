package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.output.PatientOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PatientRegistrationService;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/superadmin")
@PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
public class SuperAdminController {

    private final TeleCallerService teleCallerService;
    private final PatientRegistrationService patientRegistrationService;

    @GetMapping("/telecaller")
    public ResponseEntity<List<TeleCallerOutputDTO>> getAllTeleCallers() {
        log.info("Controller called for getting all telecallers");

        return new ResponseEntity<>(teleCallerService.getAllNotDeleted(), HttpStatus.OK);
    }

    @GetMapping("/patient")
    public ResponseEntity<List<PatientOutputDTO>> getAllPatients() {
        log.info("Controller called for getting all patients");

        return new ResponseEntity<>(patientRegistrationService.getAll(), HttpStatus.OK);
    }
}
