package com.robotrack.web_app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/Robotrack_WebApp");
        config.setUsername("system-user");
        config.setPassword("100415");
        config.setMaximumPoolSize(10); // Максимальное количество соединений в пуле
        dataSource = new HikariDataSource(config);
        System.out.println("Пул соединений с базой данных инициализирован.");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Получаем соединение из пула
    }

    // Метод для проверки, существует ли пользователь с указанным номером телефона
    public static Boolean is_old(String phone_number) {
        String query = "SELECT COUNT(*) FROM users WHERE phone_number = ?";
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Если количество больше 0, значит запись существует
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Если произошла ошибка или запись не найдена
    }

    // Метод для добавления нового пользователя в таблицу users
    public static boolean add_user(String firstName, String patronymic, String lastName, String birthDate, String phoneNumber, String password, int role_id) {
        String query = "INSERT INTO users (first_name, patronymic, last_name, birth_date, phone_number, password, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, patronymic);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, birthDate);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, password);
            preparedStatement.setInt(7, role_id);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Возвращает true, если пользователь был добавлен
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Возвращает false в случае ошибки
        }
    }

    public static String get_role_name(int role_id) {
        String roleName = null;
        String query = "SELECT name FROM roles WHERE id = ?"; // SQL-запрос для получения name по role_id
    
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, role_id); // Устанавливаем значение role_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                roleName = resultSet.getString("name"); // Получаем name
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return roleName; // Возвращаем найденное имя роли или null, если не найдено
    }

    // Новый метод для получения имени роли по role_id
    public static String getRoleNameById(int role_id) {
        String roleName = null;
        String query = "SELECT name FROM roles WHERE id = ?"; // SQL-запрос для получения name по role_id

        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, role_id); // Устанавливаем значение role_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос

            if (resultSet.next()) { // Если есть результат
                roleName = resultSet.getString("name"); // Получаем name
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }

        return roleName;
        } // Возвращаем найденное имя роли или null, если не
    } 