package com.beehyv.tbalert.tbalertbackend.controller;


import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/telecaller")
@PreAuthorize("hasAuthority('ROLE_StateCoordinator')")
public class TeleCallerController {

    private final TeleCallerService teleCallerService;
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<TeleCallerOutputDTO> add(@RequestBody @Valid TeleCallerInputDTO teleCallerInputDTO) {
        return new ResponseEntity<>(teleCallerService.add(teleCallerInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeleCallerOutputDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(teleCallerService.getById(id), HttpStatus.OK);
    }

    public ResponseEntity<PersonOutputDTO> getByPersonId(@PathVariable Long id) {
        return new ResponseEntity<>(teleCallerService.getByPersonId(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonOutputDTO> getByPersonEmail(@PathVariable String email) {
        return new ResponseEntity<>(personService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<TeleCallerOutputDTO>> getByState(@PathVariable String name) {
        return new ResponseEntity<>(teleCallerService.getByState(name), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeleCallerOutputDTO>> getAll() {
        return new ResponseEntity<>(teleCallerService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeleCallerOutputDTO> delete(@PathVariable Long id) {
        log.info("Controller called for deleting telecaller: {}", id);
        teleCallerService.deleteTeleCaller(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<TeleCallerOutputDTO>> getByTeleCallerName(@PathVariable String name) {
        return new ResponseEntity<>(teleCallerService.getByName(name),HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<TeleCallerOutputDTO> updateTeleCaller(@PathVariable Long id, @RequestBody TeleCallerInputDTO teleCallerInputDTO) {
        return new ResponseEntity<>(teleCallerService.updateTeleCaller(id,teleCallerInputDTO),HttpStatus.OK);
    }
}
