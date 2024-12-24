package com.robotrack.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParentController {
    
    @GetMapping("/parent/profile")
    public String get_parent_profile() {
        return "parent_profile"; // Страница для родителей
    }

}
