package com.robotrack.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser (@RequestParam String firstName,
                                @RequestParam(required = false) String middleName,
                                @RequestParam String lastName,
                                @RequestParam String birthDate,
                                @RequestParam String role,
                                @RequestParam String password) {

        return "redirect:/login"; 
    }
}