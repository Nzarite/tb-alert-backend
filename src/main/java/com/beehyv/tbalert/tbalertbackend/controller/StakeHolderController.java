package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.output.StakeHolderInputDTO;
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
@RequestMapping("/stakeholder")
@PreAuthorize("hasAuthority('ROLE_FieldCoordinator')")
public class StakeHolderController {

    private final GPHeadService GPHeadService;
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<StakeHolderInputDTO> add(@RequestBody @Valid com.beehyv.tbalert.tbalertbackend.dto.input.StakeHolderInputDTO gpHeadInputDTO) {
        return new ResponseEntity<>(GPHeadService.add(gpHeadInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StakeHolderInputDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(GPHeadService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonOutputDTO> getByPersonEmail(@PathVariable String email) {
        return new ResponseEntity<>(personService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<StakeHolderInputDTO>> getByState(@PathVariable String name) {
        return new ResponseEntity<>(GPHeadService.getByState(name), HttpStatus.OK);
    }

    @GetMapping("/state/{state}/name/{name}")
    public ResponseEntity<List<StakeHolderInputDTO>> getByStateAndName(@PathVariable String state, @PathVariable String name) {
        log.info("Controller called for Getting patient by state and name: {}", state);

        return new ResponseEntity<>(GPHeadService.getGPByState(state, name), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StakeHolderInputDTO> delete(@PathVariable Long id) {
        log.info("Controller called for deleting gram panchayat: {}", id);
        GPHeadService.deleteGPHead(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<StakeHolderInputDTO>> getByGPName(@PathVariable String name) {
        return new ResponseEntity<>(GPHeadService.getByName(name), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<StakeHolderInputDTO> updateGP(@PathVariable Long id, @RequestBody com.beehyv.tbalert.tbalertbackend.dto.input.StakeHolderInputDTO gpHeadInputDTO) {
        return new ResponseEntity<>(GPHeadService.updateGPHead(id, gpHeadInputDTO), HttpStatus.OK);
    }
}
