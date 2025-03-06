package com.beehyv.tbalert.tbalertbackend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserDetailsUtil {

    public List<String> getUserRolesFromKeycloak() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new RuntimeException("JWT Token is missing or invalid");
        }

        // Extract resource_access -> tb-alert-frontend -> roles
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey("tb-alert-frontend")) {
            throw new RuntimeException("No roles found in Keycloak token");
        }

        Map<String, Object> tbAlertFrontend = (Map<String, Object>) resourceAccess.get("tb-alert-frontend");
        return (List<String>) tbAlertFrontend.get("roles");
    }
}
