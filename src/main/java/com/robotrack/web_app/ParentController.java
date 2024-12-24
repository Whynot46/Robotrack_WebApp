package com.robotrack.web_app;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParentController {
    
    @GetMapping("/parent/profile")
    public String show_parent_profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String phone_number = userDetails.getUsername();
            User user = DataBase.get_user(phone_number);
            User child = DataBase.get_user(user.get_child_id());
            
            model.addAttribute("user", user);
            model.addAttribute("child", child);
        } else {
            return "redirect:/login"; // Перенаправление на страницу входа
        }
        
        return "parent_profile"; // Return the view name
    }

    @GetMapping("/parent/child_lessons")
    public String show_child_lessons(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String phone_number = userDetails.getUsername();
            User user = DataBase.get_user(phone_number);
            
            model.addAttribute("user", user);
        } else {
            return "redirect:/login"; // Перенаправление на страницу входа
        }
        
        return "child_lessons"; // Return the view name
    }

}
