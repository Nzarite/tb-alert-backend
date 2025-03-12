package com.beehyv.tbalert.tbalertbackend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class EmailService {

    private final RestTemplate restTemplate;
    public EmailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Async
    public void sendPasswordResetEmail(String userId, String accessToken) {
        String emailActionUrl = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/execute-actions-email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<List<String>> emailRequest = new HttpEntity<>(List.of("UPDATE_PASSWORD"), headers);

        try {
            restTemplate.exchange(emailActionUrl, HttpMethod.PUT, emailRequest, Void.class);
            log.info("Password reset email sent successfully to user ID: {}", userId);
        } catch (HttpClientErrorException e) {
            log.error("Failed to send password reset email: {}", e.getMessage());
        }
    }
}
