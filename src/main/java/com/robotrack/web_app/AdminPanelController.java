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
    public String showAdminPanel(Model model) {
        return "admin_panel";
    }

    @GetMapping("/admin_panel/users")
    public String adminPanel(Model model) {
        List<User> users = new ArrayList<>();
        String query = "SELECT first_name, patronymic, last_name, birth_date, phone_number, password, role_id FROM users"; // Предполагается, что role_id хранится в базе данных

        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Извлекаем данные из результата
                String firstName = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String lastName = resultSet.getString("last_name");
                String birthDate = resultSet.getString("birth_date");
                String phoneNumber = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id"); // Получаем role_id из результата

                // Создаем объект Role на основе roleId
                Role role = new Role(roleId); // Предполагается, что у вас есть соответствующий конструктор в классе Role

                // Создаем объект User
                User user = new User(firstName, patronymic, lastName, birthDate, phoneNumber, password, role);
                users.add(user); // Добавляем пользователя в список
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("users", users); // Добавляем список пользователей в модель
        return "users_list"; // Возвращаем имя шаблона
    }
}