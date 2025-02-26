package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
import com.beehyv.tbalert.tbalertbackend.service.StateHeadService;
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
@RequestMapping("/statehead")
@PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
public class StateHeadController {

    private final StateHeadService stateHeadService;
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<StateHeadOutputDTO> add(@RequestBody @Valid StateHeadInputDTO stateHeadInputDTO) {
        return new ResponseEntity<>(stateHeadService.add(stateHeadInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDTO> findByPersonId(@PathVariable Long id) {
        return new ResponseEntity<>(stateHeadService.findByPersonId(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonOutputDTO> getByPersonEmail(@PathVariable String email) {
        return new ResponseEntity<>(personService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StateHeadOutputDTO>> getAll() {
        return new ResponseEntity<>(stateHeadService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStateHead(@PathVariable Long id) {
        log.info("Controller called for deleting statehead: {}", id);
        stateHeadService.deleteStateHead(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
