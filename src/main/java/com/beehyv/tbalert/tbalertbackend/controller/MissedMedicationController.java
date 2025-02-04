package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.MissedMedicationInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MissedMedicationOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.MissedMedication;
import com.beehyv.tbalert.tbalertbackend.service.MissedMedicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missedmedication")
@AllArgsConstructor
public class MissedMedicationController {

    private MissedMedicationService missedMedicationService;

    @PostMapping("/{id}")
    public ResponseEntity<List<MissedMedicationOutputDTO>> missedMedication(@RequestBody @Valid List<MissedMedicationInputDTO> missedMedicationInputDTOS, @PathVariable int id) {
        return new ResponseEntity<>(missedMedicationService.add(id,missedMedicationInputDTOS), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MissedMedicationOutputDTO>> missedMedication(@PathVariable int id) {
        return new ResponseEntity<List<MissedMedicationOutputDTO>>(missedMedicationService.get(id),HttpStatus.FOUND);
    }
}
