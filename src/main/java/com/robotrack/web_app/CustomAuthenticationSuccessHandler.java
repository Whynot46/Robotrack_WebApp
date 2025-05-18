package com.robotrack.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {
        
        String phoneNumber = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");
        
        logger.info("User {} successfully authenticated with role: {}", phoneNumber, role);
        
        String targetUrl = determineTargetUrl(role);
        logger.info("Redirecting to: {}", targetUrl);
        
        response.sendRedirect(targetUrl);
    }

    private String determineTargetUrl(String role) {
        switch (role) {
            case "ROLE_ADMIN":
                return "/admin/profile";
            case "ROLE_TEACHER":
                return "/teacher/profile";
            case "ROLE_PARENT":
                return "/parent/profile";
            case "ROLE_STUDENT":
                return "/student/profile";
            default:
                return "/";
        }
    }
}