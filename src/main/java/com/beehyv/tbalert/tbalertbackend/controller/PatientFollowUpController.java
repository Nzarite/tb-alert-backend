package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PatientFollowUpInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PatientFollowUpOutputForFrontEndDto;
import com.beehyv.tbalert.tbalertbackend.service.PatientFollowUpService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/followup")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_Telecaller')")
public class PatientFollowUpController {

    private final PatientFollowUpService patientFollowUpService;

    @PreAuthorize("hasAuthority('ROLE_Telecaller') || hasAuthority('ROLE_GpHead')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientFollowUpOutputForFrontEndDto> getPatientFollowUp(@PathVariable String id) {
            log.info("Controller called for getPatientFollowUp: {}", id);
            return new ResponseEntity<>(patientFollowUpService.get(id), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PatientFollowUpOutputDTO> postPatientFollowUp(@PathVariable String id, @RequestBody @Valid PatientFollowUpInputDTO patientFollowUpInputDTO) {

        log.info("Controller called for postPatientFollowUp: {}", id);
        return new ResponseEntity<>(patientFollowUpService.add(id, patientFollowUpInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientFollowUpOutputDTO> updatePatientFollowUp(@PathVariable String id, @RequestBody @Valid PatientFollowUpInputDTO patientFollowUpInputDTO) {
        log.info("Controller called for updatePatientFollowUp: {}", id);
        return new ResponseEntity<>(patientFollowUpService.update(id,patientFollowUpInputDTO),HttpStatus.ACCEPTED);

    }
}
