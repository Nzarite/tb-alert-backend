package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.GramPanchayatInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.GramPanchayatOutputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.GramPanchayat;
import com.beehyv.tbalert.tbalertbackend.service.GramPanchayatService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
