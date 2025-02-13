package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.service.ReportsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("report")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class ReportsController {

    private final ReportsService reportsService;


    @GetMapping("/patient/filter")
    public ResponseEntity<HttpStatus>getReportsFilter(@RequestBody Map<String,Object> filter) throws Exception {
        reportsService.getPatients(filter);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dead")
    public ResponseEntity<Integer> getDeadPatients() throws IOException {
        log.info("Controller called for Getting dead patients");
        return new ResponseEntity<>(reportsService.getAllDead(), HttpStatus.OK);
    }

    @GetMapping("/telecaller")
    public ResponseEntity<HttpStatus>getTeleCallerOfAState(@RequestBody String state)
    {
        log.info("Controller called for Getting tele caller for state: {}", state);
        reportsService.getTeleCallerOfAState(state);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
