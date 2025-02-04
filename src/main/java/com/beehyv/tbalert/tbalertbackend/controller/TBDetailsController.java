package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.TBDetailsInputDTO;
import com.beehyv.tbalert.tbalertbackend.service.TBDetailsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tbdetails")
@AllArgsConstructor
public class TBDetailsController {

    private final TBDetailsService tbDetailsService;

    @GetMapping("/{patientId}")
    public ResponseEntity<?> getTBDetails(@PathVariable("patientId") Integer patientId) {
        return new ResponseEntity<>(tbDetailsService.getTBDetails(patientId), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTBDetails(@RequestBody @Valid TBDetailsInputDTO tbDetailsInputDTO) {
        return new ResponseEntity<>(tbDetailsService.registerTBDetails(tbDetailsInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<?> updateTBDetails(@RequestBody @Valid TBDetailsInputDTO tbDetailsInputDTO, @PathVariable Integer patientId) {
        return new ResponseEntity<>(tbDetailsService.updateTBDetails(tbDetailsInputDTO, patientId), HttpStatus.OK);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deleteTBDetails(@PathVariable Integer patientId) {
        tbDetailsService.deleteTBDetails(patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
