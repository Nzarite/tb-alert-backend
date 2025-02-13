package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.StateHeadService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/statehead")
public class StateHeadController {

    private final StateHeadService stateHeadService;

    @PostMapping("/register")
    public ResponseEntity<PersonOutputDTO> add(@RequestBody @Valid PersonInputDTO personInputDTO)
    {
        return new ResponseEntity<>(stateHeadService.add(personInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDTO> findByPersonId(@PathVariable Long id)
    {
        return new ResponseEntity<>(stateHeadService.findByPersonId(id), HttpStatus.OK);
    }
}
