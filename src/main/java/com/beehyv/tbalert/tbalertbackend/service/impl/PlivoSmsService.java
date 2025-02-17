package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.models.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PlivoSmsService {

    public void sendSms(String src, String dest, String message) {
        try {
            Message.creator(src, dest, message).create();
            log.info("SMS sent to {}", dest);
        } catch (PlivoRestException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
