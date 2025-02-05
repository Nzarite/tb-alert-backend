package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.NikshayInputDTO;
import com.beehyv.tbalert.tbalertbackend.service.NikshayMitraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nikshaymitra")
@AllArgsConstructor
public class NikshayMitraController {

    private final NikshayMitraService nikshayMitraService;

    @GetMapping("/{patientId}")
    public ResponseEntity<?> getNikshayMitraDetails(@PathVariable int patientId) {
        return new ResponseEntity<>(nikshayMitraService.getNikshayDetails(patientId), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNikshayDetails(@RequestBody @Valid NikshayInputDTO nikshayInputDTO) {
        return new ResponseEntity<>(nikshayMitraService.registerNikshayDetails(nikshayInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<?> updateNikshayDetails(@PathVariable int patientId, @RequestBody @Valid NikshayInputDTO nikshayInputDTO) {
        return new ResponseEntity<>(nikshayMitraService.updateNikshayDetails(patientId, nikshayInputDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deleteNikshayDetails(@PathVariable int patientId) {
        nikshayMitraService.deleteNikshayDetails(patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
