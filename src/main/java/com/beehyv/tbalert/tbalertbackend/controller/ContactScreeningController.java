package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.ContactScreeningDTO;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ContactScreeningController {

    private final ContactScreeningService contactScreeningService;

    @GetMapping("getContactScreeningDetails/{patientId}")
    public ResponseEntity<ContactScreeningDTO> getContactScreening(@PathVariable Integer patientId) {
        return new ResponseEntity<>(contactScreeningService.getContactScreeningById(patientId), HttpStatus.FOUND);
    }

    @PostMapping("saveContactScreeningDetails")
    public ResponseEntity<?> saveContactScreeningDetails(@RequestBody @Valid ContactScreeningDTO contactScreeningDTO) {
        contactScreeningService.saveContactScreening(contactScreeningDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @PutMapping("updateContactScreeningDetails")
    public ResponseEntity<?> updateContactScreeningDetails(@RequestBody @Valid ContactScreeningDTO contactScreeningDTO) {
        contactScreeningService.saveContactScreening(contactScreeningDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
