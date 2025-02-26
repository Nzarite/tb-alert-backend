package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.service.KeycloakAdminService;
import com.beehyv.tbalert.tbalertbackend.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final KeycloakAdminService keycloakAdminService;
    private final RestTemplate restTemplate;
    private final EmailService emailService;

    public KeycloakUserServiceImpl(KeycloakAdminService keycloakAdminService, RestTemplate restTemplate, EmailService emailService) {
        this.keycloakAdminService = keycloakAdminService;
        this.restTemplate = restTemplate;
        this.emailService = emailService;
    }

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id-id}")
    private String clientIdId;

    @Override
    public String createUser(String email, String role) {
        String accessToken = keycloakAdminService.getAdminAccessToken();
        if (accessToken == null) {
            log.error("Failed to get admin access token");
            return "Failed to get admin access token";
        }

        String url = keycloakUrl + "/admin/realms/" + realm + "/users";
        HttpHeaders headers = createHeaders(accessToken);
        Map<String, Object> userPayload = Map.of(
                "username", email,
                "email", email,
                "emailVerified", true,
                "enabled", true
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("User creation failed: {}", response.getBody());
                return "User creation failed: " + response.getBody();
            }
            return assignUserRole(email, role, accessToken);
        } catch (HttpClientErrorException e) {
            log.error("Error creating user: {}", e.getMessage());
            return "Error creating user: " + e.getMessage();
        }
    }

    public String assignUserRole(String email, String role, String accessToken) {
        try {
            // Step 1: Get User ID from Keycloak
            String searchUrl = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + email;
            HttpHeaders headers = createHeaders(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> searchResponse = restTemplate.exchange(
                    searchUrl,
                    HttpMethod.GET,
                    entity,
                    (Class<List<Map<String, Object>>>) (Class<?>) List.class);

            if (searchResponse.getBody() == null || searchResponse.getBody().isEmpty()) {
                log.error("User not found in Keycloak");
                return "User not found in Keycloak";
            }

            String userId = (String) searchResponse.getBody().get(0).get("id");

            // Step 2: Get Role details from Keycloak
            String roleUrl = keycloakUrl + "/admin/realms/" + realm + "/clients/" + clientIdId + "/roles/" + role;
            ResponseEntity<Map<String, Object>> roleResponse = restTemplate.exchange(
                    roleUrl,
                    HttpMethod.GET,
                    entity,
                    (Class<Map<String, Object>>) (Class<?>) Map.class);

            if (roleResponse.getBody() == null) {
                log.error("Role not found in Keycloak");
                return "Role not found in Keycloak";
            }

            // Step 3: Assign role to user
            String assignRoleUrl = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/clients/" + clientIdId;
            HttpEntity<List<Map<String, Object>>> roleRequest = new HttpEntity<>(List.of(roleResponse.getBody()), headers);
            restTemplate.postForEntity(assignRoleUrl, roleRequest, Void.class);

            // Step 4: Send password reset email
            emailService.sendPasswordResetEmail(userId, accessToken);

            log.info("User created and role assigned successfully");
            return "User created and role assigned successfully";

        } catch (HttpClientErrorException e) {
            log.error("Error assigning role: {}", e.getMessage());
            return "Error assigning role: " + e.getMessage();
        }
    }

    public String deleteUserByEmail(String email) {
        String accessToken = keycloakAdminService.getAdminAccessToken();
        if (accessToken == null) {
            log.error("Failed to get admin access token");
            return "Failed to get admin access token";
        }

        try {
            // Step 1: Search for the user in Keycloak
            String searchUrl = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + email;
            HttpHeaders headers = createHeaders(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> searchResponse = restTemplate.exchange(
                    searchUrl,
                    HttpMethod.GET,
                    entity,
                    (Class<List<Map<String, Object>>>) (Class<?>) List.class);

            if (searchResponse.getBody() == null || searchResponse.getBody().isEmpty()) {
                log.warn("User with email {} not found in Keycloak", email);
                return "User not found in Keycloak";
            }

            String userId = (String) searchResponse.getBody().get(0).get("id");

            // Step 2: Delete the user
            String deleteUrl = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId;
            restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, Void.class);

            log.info("User with email {} deleted successfully from Keycloak", email);
            return "User deleted successfully";

        } catch (HttpClientErrorException e) {
            log.error("Error deleting user: {}", e.getMessage());
            return "Error deleting user: " + e.getMessage();
        }
    }


    // Utility method to create HTTP headers with authentication
    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        return headers;
    }

}
