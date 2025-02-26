package com.beehyv.tbalert.tbalertbackend.service;

public interface KeycloakUserService {
    String createUser(String email, String role);

    String assignUserRole(String email, String role, String accessToken);

    void sendPasswordResetEmail(String userId, String accessToken);

    String deleteUserByEmail(String email);
}
