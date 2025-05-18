package com.robotrack.web_app;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String phone_number = userDetails.getUsername();
            User user = DataBase.get_user(phone_number);
            model.addAttribute("user", user);
        } else {
            return "redirect:/login";
        }

        return "statistics";
    }
}