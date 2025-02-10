package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.service.ReportsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("report")
@AllArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/patient/all")
    public ResponseEntity<HttpStatus>getAllPatients() throws IOException{
        reportsService.getAllPatients();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dead")
    public ResponseEntity<Integer> getDeadPatients() throws IOException {
        log.info("Controller called for Getting dead patients");
        return new ResponseEntity<>(reportsService.getAllDead(), HttpStatus.OK);
    }
}
