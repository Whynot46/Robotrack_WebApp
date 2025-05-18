package com.robotrack.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Неверный номер телефона или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        if (registered != null) {
            model.addAttribute("message", "Регистрация прошла успешно! Теперь вы можете войти.");
        }
        
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String firstName,
            @RequestParam String patronymic,
            @RequestParam String lastName,
            @RequestParam String phoneNumber,
            @RequestParam String birthDate,
            @RequestParam int roleId,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        
        // Валидация ввода
        if (firstName == null || firstName.isEmpty() || 
            lastName == null || lastName.isEmpty() ||
            phoneNumber == null || phoneNumber.isEmpty() ||
            password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Заполните все обязательные поля");
            return "redirect:/register";
        }
        
        // Проверка существующего пользователя
        if (DataBase.is_old(phoneNumber)) {
            redirectAttributes.addFlashAttribute("error", "Пользователь с таким номером телефона уже существует");
            return "redirect:/register";
        }
        
        // Регистрация нового пользователя
        if (DataBase.add_user(firstName, patronymic, lastName, birthDate, 
                            phoneNumber, password, roleId)) {
            redirectAttributes.addFlashAttribute("success", "Регистрация прошла успешно! Теперь вы можете войти.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Ошибка при регистрации. Пожалуйста, попробуйте позже.");
            return "redirect:/register";
        }
    }
}