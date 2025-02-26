package com.beehyv.tbalert.tbalertbackend.controller;

import com.beehyv.tbalert.tbalertbackend.service.impl.PlivoSmsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@ConditionalOnBean(PlivoSmsService.class)
public class SMSTestController {

    private final PlivoSmsService smsService;

    @PostMapping
    public ResponseEntity<String> test(@RequestBody Map<String, String> body) {
        String recipient = body.get("recipient");
        String message = body.get("message");

        smsService.sendSms("TBALERT", recipient, message);
        return ResponseEntity.ok("OK");
    }
}
