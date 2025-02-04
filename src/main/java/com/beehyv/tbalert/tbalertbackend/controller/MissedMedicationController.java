package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/missedmedication")
@AllArgsConstructor
public class MissedMedicationController {

    private MissedMedicationService missedMedicationService;

    @PostMapping("/{id}")
    public ResponseEntity<List<MissedMedicationOutputDTO>> missedMedication(@RequestBody @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS, @PathVariable int id) {
        try {
            log.info("Controller for called adding missed medication for : {}", missedMedicationInputDTOS);
            return new ResponseEntity<>(missedMedicationService.add(id, missedMedicationInputDTOS), HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MissedMedicationOutputDTO>> missedMedication(@PathVariable int id) {
        try {
            log.info("Controller for called getting missed medication for : {}", id);
            return new ResponseEntity<List<MissedMedicationOutputDTO>>(missedMedicationService.get(id), HttpStatus.FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
