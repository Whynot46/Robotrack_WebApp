package com.robotrack.web_app;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/student/profile")
    public String show_student_profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String phone_number = userDetails.getUsername();
            User user = DataBase.get_user(phone_number);
            
            model.addAttribute("user", user); // Add the user object to the model
        } else {
            return "redirect:/login"; // Перенаправление на страницу входа
        }
        
        return "student_profile"; // Return the view name
    }

    @GetMapping("/student/student_lessons")
    public String show_student_lessons(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String phone_number = userDetails.getUsername();
            User user = DataBase.get_user(phone_number);
            
            model.addAttribute("user", user); // Add the user object to the model
        } else {
            return "redirect:/login"; // Перенаправление на страницу входа
        }
        
        return "student_lessons"; // Return the view name
    }
}
