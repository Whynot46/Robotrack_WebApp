package com.robotrack.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {

    @GetMapping("/teacher/profile")
    public String show_teacher_profile() {
        return "teacher_profile"; // Страница для учителей
    }
}
