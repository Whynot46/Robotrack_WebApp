package com.robotrack.web_app;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/student/profile")
    public String show_student_profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("Userdetails: {}", userDetails);
            String phone_number = userDetails.getUsername();
            logger.info("Userdetails username: {}", userDetails.getUsername());
            User user = DataBase.get_user(phone_number);
            logger.info("User: {}", user.toString());
            
            model.addAttribute("user", user); // Add the user object to the model
        } else {
            logger.warn("Authentication is null or user is not authenticated.");
            return "redirect:/login"; // Перенаправление на страницу входа
        }
        
        return "student_profile"; // Return the view name
    }

    @GetMapping("/student/student_lessons")
    public String show_student_lessons(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("Userdetails: {}", userDetails);
            String phone_number = userDetails.getUsername();
            logger.info("Userdetails username: {}", userDetails.getUsername());
            User user = DataBase.get_user(phone_number);
            logger.info("User: {}", user.toString());
            
            model.addAttribute("user", user); // Add the user object to the model
        } else {
            logger.warn("Authentication is null or user is not authenticated.");
            return "redirect:/login"; // Перенаправление на страницу входа
        }
        
        return "student_lessons"; // Return the view name
    }
}
