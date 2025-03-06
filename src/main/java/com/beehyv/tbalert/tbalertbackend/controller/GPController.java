package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.GPHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GPHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.GPHeadService;
import com.beehyv.tbalert.tbalertbackend.service.PersonService;
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
@RequestMapping("/gphead")
@PreAuthorize("hasAuthority('ROLE_FieldCoordinator')")
public class GPController {

    private final GPHeadService GPHeadService;
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<GPHeadOutputDTO> add(@RequestBody @Valid GPHeadInputDTO gpHeadInputDTO) {
        return new ResponseEntity<>(GPHeadService.add(gpHeadInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GPHeadOutputDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(GPHeadService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonOutputDTO> getByPersonEmail(@PathVariable String email) {
        return new ResponseEntity<>(personService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<GPHeadOutputDTO>> getByState(@PathVariable String name) {
        return new ResponseEntity<>(GPHeadService.getByState(name), HttpStatus.OK);
    }

    @GetMapping("/state/{state}/name/{name}")
    public ResponseEntity<List<GPHeadOutputDTO>> getByStateAndName(@PathVariable String state, @PathVariable String name) {
        log.info("Controller called for Getting patient by state and name: {}", state);

        return new ResponseEntity<>(GPHeadService.getGPByState(state, name), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GPHeadOutputDTO> delete(@PathVariable Long id) {
        log.info("Controller called for deleting gram panchayat: {}", id);
        GPHeadService.deleteGPHead(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<GPHeadOutputDTO>> getByGPName(@PathVariable String name) {
        return new ResponseEntity<>(GPHeadService.getByName(name), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GPHeadOutputDTO> updateGP(@PathVariable Long id, @RequestBody GPHeadInputDTO gpHeadInputDTO) {
        return new ResponseEntity<>(GPHeadService.updateGPHead(id, gpHeadInputDTO), HttpStatus.OK);
    }
}
