package com.robotrack.web_app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @GetMapping("/admin_panel")
    public String show_admin_panel(Model model) {
        return "admin_panel";
    }

    @GetMapping("/admin_panel/users")
    public String show_users(Model model) {
        List<User> users = DataBase.get_users();
        model.addAttribute("users", users); // Добавляем список пользователей в модель
        return "users_list"; // Возвращаем имя шаблона
    }

    @GetMapping("/admin_panel/roles")
    public String show_roles(Model model) {
        List<Role> roles = DataBase.get_roles();
        model.addAttribute("roles", roles); // Добавляем список ролей в модель
        return "roles_list"; // Возвращаем имя шаблона
    }

    @GetMapping("/admin_panel/courses")
    public String show_courses(Model model) {
        List<Course> courses = DataBase.get_courses();
        model.addAttribute("courses", courses); // Добавляем список ролей в модель
        return "courses_list"; // Возвращаем имя шаблона
    }

    @GetMapping("/admin_panel/tasks")
    public String show_tasks(Model model) {
        List<Task> tasks = DataBase.get_tasks();
        model.addAttribute("tasks", tasks); // Добавляем список ролей в модель
        return "tasks_list"; // Возвращаем имя шаблона
    }

    @GetMapping("/admin_panel/lessons")
    public String show_lessons(Model model) {
        List<Lesson> lessons = DataBase.get_lessons();
        model.addAttribute("lessons", lessons); // Добавляем список ролей в модель
        return "lessons_list"; // Возвращаем имя шаблона
    }

}