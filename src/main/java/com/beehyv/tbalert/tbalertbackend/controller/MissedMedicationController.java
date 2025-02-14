package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/missedmedication")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_Telecaller')")
public class MissedMedicationController {

    private final MissedMedicationService missedMedicationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<MissedMedicationOutputDTO>> missedMedication(@PathVariable int id) {
            log.info("Controller for called getting missed medication for : {}", id);
            return new ResponseEntity<>(missedMedicationService.get(id), HttpStatus.FOUND);
    }
}
