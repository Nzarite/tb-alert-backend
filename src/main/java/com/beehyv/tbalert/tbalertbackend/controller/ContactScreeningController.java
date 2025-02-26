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

    @PostMapping("{patientId}")
    public ResponseEntity<ContactScreeningOutputDTO> setContactScreening(@PathVariable String patientId, @RequestBody @Valid ContactScreeningInputDTO contactScreeningInputDTO) {
        return new ResponseEntity<>(contactScreeningService.setContactScreening(patientId, contactScreeningInputDTO), HttpStatus.ACCEPTED);
    }


    @PutMapping("/{patientId}")
    public ResponseEntity<ContactScreeningOutputDTO> updateContactScreening(@PathVariable String patientId, @RequestBody @Valid ContactScreeningInputDTO contactScreeningInputDTO) {
        return new ResponseEntity<>(contactScreeningService.updateContactScreening(patientId, contactScreeningInputDTO), HttpStatus.ACCEPTED);
    }
}
