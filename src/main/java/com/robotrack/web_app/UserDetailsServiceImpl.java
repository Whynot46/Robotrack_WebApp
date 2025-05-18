package com.robotrack.web_app;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        logger.info("Attempting to authenticate phone number: {}", phoneNumber);
        
        // Упрощенная нормализация - оставляем только цифры
        String normalizedPhone = phoneNumber.replaceAll("[^0-9]", "");
        
        // Если номер начинается с 7 или 8, добавляем +7
        if (normalizedPhone.startsWith("7")) {
            normalizedPhone = "+" + normalizedPhone;
        } else if (normalizedPhone.startsWith("8")) {
            normalizedPhone = "+7" + normalizedPhone.substring(1);
        } else if (!normalizedPhone.startsWith("+")) {
            normalizedPhone = "+7" + normalizedPhone;
        }
        
        logger.info("Normalized phone number: {}", normalizedPhone);
        
        User user = DataBase.get_user(normalizedPhone);
        if (user == null) {
            logger.error("User not found for phone: {}", normalizedPhone);
            throw new UsernameNotFoundException("User not found");
        }
        
        logger.info("Found user: {}", user);
        Role role = DataBase.get_role(user.get_role_id());
        
        return new org.springframework.security.core.userdetails.User(
            user.get_phone_number(), // Используем номер как есть из базы
            user.get_password_hash(),
            AuthorityUtils.createAuthorityList("ROLE_" + role.get_name().toUpperCase())
        );
    }
}