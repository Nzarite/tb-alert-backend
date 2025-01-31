package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.NikshayMitraDTO;
import com.beehyv.tbalert.tbalertbackend.service.NikshayMitraService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class NikshayMitraController {

    private final NikshayMitraService nikshayMitraService;

    @PostMapping("/nikshaymitra")
    public ResponseEntity<?> registerNikshayDetails(@RequestBody NikshayMitraDTO nikshayMitraDTO) {
        return new ResponseEntity<>(nikshayMitraService.registerNikshayDetails(nikshayMitraDTO), HttpStatus.CREATED);
    }
}
