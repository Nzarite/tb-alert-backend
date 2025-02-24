package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.StateHeadInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.StateHeadOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.StateHeadService;
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
@RequestMapping("/statehead")
public class StateHeadController {

    private final StateHeadService stateHeadService;

    @PostMapping("/register")
    public ResponseEntity<StateHeadOutputDTO> add(@RequestBody @Valid StateHeadInputDTO stateHeadInputDTO)
    {
        return new ResponseEntity<>(stateHeadService.add(stateHeadInputDTO), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StateHeadOutputDTO> findById(@PathVariable Long id)
    {
        return new ResponseEntity<>(stateHeadService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<StateHeadOutputDTO>> getStateHeadByName(@PathVariable String name) {
        return new ResponseEntity<>(stateHeadService.getStateHeadByName(name),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StateHeadOutputDTO> updateStateHead(@PathVariable Long id, @RequestBody @Valid StateHeadInputDTO stateHeadInputDTO)
    {
        return new ResponseEntity<>(stateHeadService.update(id,stateHeadInputDTO),HttpStatus.OK);
    }
}
