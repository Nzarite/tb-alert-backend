package com.beehyv.tbalert.tbalertbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/webhooks")
public class PlivoWebhookController {

    @PostMapping("/delivery")
    public void handleDeliveryReport(@RequestBody Map<String, String> payload) {
        log.info("Delivery Report: {}", payload);
    }
}
