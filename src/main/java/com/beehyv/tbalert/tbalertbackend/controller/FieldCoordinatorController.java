package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.FieldCoordinatorInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.FieldCoordinatorOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.FieldCoordinatorService;
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
@RequestMapping("/fieldcoordinator")
@PreAuthorize("hasAuthority('ROLE_StateCoordinator')")
public class FieldCoordinatorController {

    private final FieldCoordinatorService fieldCoordinatorService;
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<FieldCoordinatorOutputDTO> add(@RequestBody @Valid FieldCoordinatorInputDTO fieldCoordinatorInputDTO) {
        return new ResponseEntity<>(fieldCoordinatorService.add(fieldCoordinatorInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldCoordinatorOutputDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(fieldCoordinatorService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonOutputDTO> getByPersonEmail(@PathVariable String email) {
        return new ResponseEntity<>(personService.getByEmail(email), HttpStatus.OK);
    }

//    @GetMapping("/state/{name}")
//    public ResponseEntity<List<FieldCoordinatorOutputDTO>> getByState(@PathVariable String name) {
//        return new ResponseEntity<>(fieldCoordinatorService.getByState(name), HttpStatus.OK);
//    }
//
//    @GetMapping("/state/{state}/name/{name}")
//    public ResponseEntity<List<FieldCoordinatorOutputDTO>> getByStateAndName(@PathVariable String state, @PathVariable String name) {
//        log.info("Controller called for Getting patient by state and name: {}", state);
//
//        return new ResponseEntity<>(fieldCoordinatorService.getGPByState(state,name), HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FieldCoordinatorOutputDTO> delete(@PathVariable Long id) {
        log.info("Controller called for deleting gram panchayat: {}", id);
        fieldCoordinatorService.deleteFieldCoordinator(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<FieldCoordinatorOutputDTO>> getByGPName(@PathVariable String name) {
        return new ResponseEntity<>(fieldCoordinatorService.getByName(name), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<FieldCoordinatorOutputDTO> updateGP(@PathVariable Long id, @RequestBody FieldCoordinatorInputDTO fieldCoordinatorInputDTO) {
        return new ResponseEntity<>(fieldCoordinatorService.updateFieldCoordinator(id, fieldCoordinatorInputDTO), HttpStatus.OK);
    }
}
