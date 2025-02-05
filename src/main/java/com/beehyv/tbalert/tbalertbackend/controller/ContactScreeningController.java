package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ContactScreeningController {

    private final ContactScreeningService contactScreeningService;

    @GetMapping("getContactScreeningDetails/{patientId}")
    public ResponseEntity<ContactScreeningOutputDTO> getContactScreening(@PathVariable Integer patientId) {
        return new ResponseEntity<>(contactScreeningService.getContactScreeningById(patientId), HttpStatus.FOUND);
    }

    @PostMapping("saveContactScreeningDetails")
    public ResponseEntity<?> saveContactScreeningDetails(@RequestBody @Valid ContactScreeningInputDTO contactScreeningInputDTO) {
        contactScreeningService.saveContactScreening(contactScreeningInputDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @PutMapping("updateContactScreeningDetails")
    public ResponseEntity<?> updateContactScreeningDetails(@RequestBody @Valid ContactScreeningInputDTO contactScreeningInputDTO) {
        contactScreeningService.saveContactScreening(contactScreeningInputDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
