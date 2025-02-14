package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.service.ReportsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("report")
@AllArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;


    @PostMapping("/patient/filter")
    public ResponseEntity<byte[]>getReportsFilter(@RequestBody Map<String,Object> filter) {
        byte[] excelData = reportsService.getPatients(filter);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=patients.xlsx")
                .body(excelData);
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
