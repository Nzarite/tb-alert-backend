package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.dto.NikshayMitraDTO;
import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/register")
public class NikshayMitraController {

    @PostMapping("/nikshaymitra")
    public ResponseEntity<NikshayMitraDTO> registerNikshayDetails(@RequestBody NikshayMitraDTO nikshayMitraDTO) {

    }
}
