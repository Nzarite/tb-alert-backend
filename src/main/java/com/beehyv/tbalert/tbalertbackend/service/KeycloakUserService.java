package com.beehyv.tbalert.tbalertbackend.service;

public interface KeycloakUserService {
    String createUser(String email, String role);
}
