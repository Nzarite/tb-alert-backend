package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.StateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/state")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_SuperAdmin')")
public class StateController {

    private final StateService stateService;

    @PostMapping("/add")
    public ResponseEntity<StateOutputDTO> addState(@RequestBody @Valid StateInputDTO stateInputDTO) {
        log.info("Controller called for add state: {}", stateInputDTO.toString());

        return new ResponseEntity<>(stateService.add(stateInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StateOutputDTO>> getAllStates() {
        log.info("Controller called for getAllStates");

        return new ResponseEntity<>(stateService.getAllStates(), HttpStatus.OK);
    }

    @DeleteMapping("/{state}")
    public ResponseEntity<?> deleteState(@PathVariable("state") String state) {
        log.info("Controller called for delete state: {}", state);

        stateService.delete(state);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
