package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.service.ReportsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@PreAuthorize("hasAuthority('ROLE_Telecaller') || hasAuthority('ROLE_GpHead')")
public class ReportsController {

    private final ReportsService reportsService;


    @PostMapping("/patient/filter")
    public ResponseEntity<byte[]> getReportsFilter(@RequestBody Map<String, Object> filter) throws IOException {
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

    @PostMapping("/telecaller")
    public ResponseEntity<byte[]> getTeleCallerOfAState(@RequestBody  Map<String,Object>filter) throws IOException {
        log.info("Controller called for Getting telecallers for state: {}", filter);
        byte[] excelData = reportsService.getTeleCallerOfAState(filter);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=telecaller.xlsx")
                .body(excelData);
    }

    @PostMapping("/statehead")
    public ResponseEntity<byte[]> getStateHeads(@RequestBody Map<String,Object>filter) throws IOException {
        log.info("Controller called for Getting state heads");
        byte[] excelData = reportsService.getStateHeads(filter);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statehead.xlsx")
                .body(excelData);
    }

    @PostMapping("/patient/followup")
    public ResponseEntity<byte[]> getPatientFollowUps(@RequestBody Map<String, Object> filter) throws IOException {
        log.info("Controller called for Getting patient follow ups");
        byte[] excelData = reportsService.getPatientFollowUp(filter);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=patientfollowup.xlsx")
                .body(excelData);

    }

    @PostMapping("/patient/followup/today")
    public ResponseEntity<byte[]>getPatientFollowUpToday(@RequestBody Map<String,Object>filter) throws IOException {
        log.info("Controller called for Getting patient follow up today");
        byte[] excelData=reportsService.getPatientFollowUpForToday(filter);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=followupsfortoday.xlsx")
                .body(excelData);
    }

}
