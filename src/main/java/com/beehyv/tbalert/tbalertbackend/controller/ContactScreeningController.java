package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.ContactScreeningOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.ContactScreeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contactscreening")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_Telecaller')")
public class ContactScreeningController {

    private final ContactScreeningService contactScreeningService;

    @GetMapping("/{patientId}")
    public ResponseEntity<ContactScreeningOutputDTO> getContactScreening(@PathVariable String patientId) {
        return new ResponseEntity<>(contactScreeningService.getContactScreeningById(patientId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveContactScreeningDetails(@RequestBody @Valid ContactScreeningInputDTO contactScreeningInputDTO) {
        contactScreeningService.saveContactScreening(contactScreeningInputDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateContactScreeningDetails(@RequestBody @Valid ContactScreeningInputDTO contactScreeningInputDTO) {
        contactScreeningService.saveContactScreening(contactScreeningInputDTO);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
