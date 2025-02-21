package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.service.KeycloakAdminService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final RestTemplate restTemplate;
    public KeycloakAdminServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Override
    public String getAdminAccessToken() {

        String url = keycloakUrl + "/realms/tb-alert/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("access_token");
            }
            log.error("Failed to fetch Keycloak access token. Response: {}", response.getBody());
        } catch (Exception e) {
            log.error("Exception while fetching Keycloak access token", e);
        }
        return null;
    }
}
