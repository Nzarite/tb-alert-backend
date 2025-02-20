package com.beehyv.tbalert.tbalertbackend.controller;


import com.beehyv.tbalert.tbalertbackend.dto.input.TeleCallerInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.TeleCallerOutputDTO;
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

    @PostMapping("/register")
    public ResponseEntity<TeleCallerOutputDTO>add(@RequestBody @Valid TeleCallerInputDTO teleCallerInputDTO) {
        return new ResponseEntity<>(teleCallerService.add(teleCallerInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDTO> getByPersonId(@PathVariable Long id) {
        return new ResponseEntity<>(teleCallerService.getByPersonId(id),HttpStatus.OK);
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<TeleCallerOutputDTO>> getByState(@PathVariable String name) {
        return new ResponseEntity<>(teleCallerService.getByState(name),HttpStatus.OK);
    }

}
