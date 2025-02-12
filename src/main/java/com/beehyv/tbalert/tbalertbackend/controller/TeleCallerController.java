package com.beehyv.tbalert.tbalertbackend.controller;


import com.beehyv.tbalert.tbalertbackend.dto.input.PersonInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.PersonOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.TeleCallerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/telecaller")
public class TeleCallerController {

    private final TeleCallerService teleCallerService;

    @PostMapping("/register")
    public ResponseEntity<PersonOutputDTO>add(@RequestBody @Valid PersonInputDTO personInputDTO) {
        return new ResponseEntity<>(teleCallerService.add(personInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDTO> getByPersonId(@PathVariable Long id) {
        return new ResponseEntity<>(teleCallerService.getByPersonId(id),HttpStatus.OK);
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<PersonOutputDTO>> getByState(@PathVariable String name) {
        return new ResponseEntity<>(teleCallerService.getByState(name),HttpStatus.OK);
    }

}
