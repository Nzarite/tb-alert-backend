package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.GramPanchayatService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grampanchayat")
@AllArgsConstructor
@Slf4j
public class GramPanchayatController {

    private final GramPanchayatService gramPanchayatService;

    @PostMapping("/add")
    public ResponseEntity<GramPanchayatOutputDTO> addGramPanchayat(@RequestBody @Valid GramPanchayatInputDTO gramPanchayatInputDTO) {
        return new ResponseEntity<>(gramPanchayatService.addGramPanchayat(gramPanchayatInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GramPanchayatOutputDTO> getGramPanchayat(@PathVariable Long id) {
        return new ResponseEntity<>(gramPanchayatService.getById(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GramPanchayatOutputDTO>> getAll() {
        return new ResponseEntity<>(gramPanchayatService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<GramPanchayatOutputDTO>> getAllByMandalId(@PathVariable Long id) {
        return new ResponseEntity<>(gramPanchayatService.getAllByMandalId(id),HttpStatus.OK);
    }
}
