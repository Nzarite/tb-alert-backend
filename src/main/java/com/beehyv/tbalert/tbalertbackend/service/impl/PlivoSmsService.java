package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.plivo.api.PlivoClient;
import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.models.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(name = "app.config.plivo-connection", havingValue = "true", matchIfMissing = false)
public class PlivoSmsService {

    private final PlivoClient plivoClient;

    public void sendSms(String src, String dest, String message) {
        try {
            Message.creator(src, dest, message)
                    .log(true)
                    .client(plivoClient)
                    .create();

            log.info("SMS sent to {}", dest);
        } catch (PlivoRestException | IOException e) {
            log.error("Failed to send SMS to {}: {}", dest, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendSms(String src, List<String> dest, String message) {
        try {
            Message.creator(src, dest, message)
                    .log(true)
                    .client(plivoClient)
                    .create();

            log.info("SMS is sent to all the following: {}", dest);
        } catch (PlivoRestException | IOException e) {
            log.error("Failed to send SMS: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
