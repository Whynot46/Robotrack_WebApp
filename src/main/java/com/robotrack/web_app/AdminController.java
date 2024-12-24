package com.robotrack.web_app;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/profile")
    public String show_admin_profile() {
        return "admin_profile"; // Страница для администраторов
    }

    @GetMapping("/current-user")
    public String getCurrentUser (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Получаем имя пользователя
            String username = authentication.getName(); // Это имя пользователя
            model.addAttribute("username", username);
            
            // Получаем principal и добавляем его в модель
            Object principal = authentication.getPrincipal();
            model.addAttribute("principal", principal);
            
            // Получаем роли пользователя
            List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            
            // Проверяем, есть ли роли
            if (!roles.isEmpty()) {
                model.addAttribute("role", roles.get(0)); // Добавляем первую роль в модель
            } else {
                model.addAttribute("role", "Нет ролей"); // Если ролей нет
            }
        } else {
            model.addAttribute("username", null);
            model.addAttribute("principal", null);
            model.addAttribute("role", null);
        }
        return "current_user"; // Возвращаем имя шаблона
    }

    @GetMapping("admin/admin_panel")
    public String show_admin_panel(Model model) {
        return "admin_panel";
    }

    @GetMapping("admin/admin_panel/users")
    public String show_users(Model model) {
        List<User> users = DataBase.get_users();
        model.addAttribute("users", users); // Добавляем список пользователей в модель
        return "users_list"; // Возвращаем имя шаблона
    }

    @GetMapping("admin/admin_panel/roles")
    public String show_roles(Model model) {
        List<Role> roles = DataBase.get_roles();
        model.addAttribute("roles", roles); // Добавляем список ролей в модель
        return "roles_list"; // Возвращаем имя шаблона
    }

    @GetMapping("admin/admin_panel/courses")
    public String show_courses(Model model) {
        List<Course> courses = DataBase.get_courses();
        model.addAttribute("courses", courses); // Добавляем список ролей в модель
        return "courses_list"; // Возвращаем имя шаблона
    }

    @GetMapping("admin/admin_panel/tasks")
    public String show_tasks(Model model) {
        List<Task> tasks = DataBase.get_tasks();
        model.addAttribute("tasks", tasks); // Добавляем список ролей в модель
        return "tasks_list"; // Возвращаем имя шаблона
    }

    @GetMapping("admin/admin_panel/lessons")
    public String show_lessons(Model model) {
        List<Lesson> lessons = DataBase.get_lessons();
        model.addAttribute("lessons", lessons); // Добавляем список ролей в модель
        return "lessons_list"; // Возвращаем имя шаблона
    }

}