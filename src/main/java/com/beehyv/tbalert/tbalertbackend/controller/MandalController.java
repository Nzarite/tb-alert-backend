package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.input.MandalInputDTO;
import com.beehyv.tbalert.tbalertbackend.dto.output.MandalOutputDTO;
import com.beehyv.tbalert.tbalertbackend.service.MandalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mandal")
@AllArgsConstructor
public class MandalController {

    private final MandalService mandalService;

    @PostMapping("/add")
    public ResponseEntity<MandalOutputDTO> addMandal(@RequestBody @Valid MandalInputDTO mandalInputDTO) {
        return new ResponseEntity<>(mandalService.addMandal(mandalInputDTO), HttpStatus.CREATED);
    }

}
