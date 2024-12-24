package com.robotrack.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String phone_number = authentication.getName(); // Получаем номер телефона
        String roles = authentication.getAuthorities().toString();
        logger.info("User  {} successfully authenticated with role: {}", phone_number, roles);
    
        String redirectUrl = determineTargetUrl(authentication);
        response.sendRedirect(redirectUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("");
    
        switch (role) {
            case "ROLE_ADMIN":
                return "/admin/profile"; // URL for admin
            case "ROLE_TEACHER":
                return "/teacher/profile"; // URL for teacher
            case "ROLE_PARENT":
                return "/parent/profile"; // URL for parent
            case "ROLE_STUDENT":
                return "/student/profile"; // URL for student
            default:
                return "/"; // Default URL
        }
    }

}