package com.robotrack.web_app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        return false;
    }
    
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
            return false;
        }
    }

    public static List<User> get_users(){
        List<User> users = new ArrayList<>();
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password, role_id FROM users"; // Предполагается, что role_id хранится в базе данных

        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String last_name = resultSet.getString("last_name");
                String birth_date = resultSet.getString("birth_date");
                String phone_number = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                int role_id = resultSet.getInt("role_id");

                Role role = new Role(role_id);
                User user = new User(id, first_name, patronymic, last_name, birth_date, phone_number, password, role);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users; 
    }

    public static User get_user(int user_id) {
        User user = null;
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password, role_id FROM users WHERE id = ?"; // SQL-запрос для получения пользователя по id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем значение user_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String last_name = resultSet.getString("last_name");
                String birth_date = resultSet.getString("birth_date");
                String phone_number = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                int role_id = resultSet.getInt("role_id"); // Получаем role_id из результата
    
                Role role = DataBase.get_role(role_id);
                user = new User(id, first_name, patronymic, last_name, birth_date, phone_number, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    public static List<Role> get_roles() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT id, name FROM roles"; // SQL-запрос для получения id и name ролей
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
    
                // Создаем объект Role
                Role role = new Role(id, name); // Используем конструктор Role с id и name
    
                roles.add(role); // Добавляем роль в список
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles; 
    }

    public static Role get_role(int role_id) {
        Role role = null; // Изначально роль равна null
        String query = "SELECT name FROM roles WHERE id = ?"; // SQL-запрос для получения роли по id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, role_id); // Устанавливаем значение role_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                String name = resultSet.getString("name");
                role = new Role(role_id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
        
        return role;
    }

    public static String get_role_name(int role_id) {
        String role_name = null;
        String query = "SELECT name FROM roles WHERE id = ?"; // SQL-запрос для получения name по role_id
    
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, role_id); // Устанавливаем значение role_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                role_name = resultSet.getString("name"); // Получаем name
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return role_name;
    }

    public static List<Course> get_courses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT id, name, age_limit FROM courses"; // SQL-запрос для получения id, name и age_limit курсов
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age_limit = resultSet.getString("age_limit");
                Course course = new Course(id, name, age_limit);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses; 
    }

    public static Course get_course(int course_id) {
        Course course = null; // Изначально курс равен null
        String query = "SELECT id, name, age_limit FROM courses WHERE id = ?"; // SQL-запрос для получения курса по id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, course_id); // Устанавливаем значение course_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age_limit = resultSet.getString("age_limit");
                course = new Course(id, name, age_limit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return course;
    }
 
    public static List<Task> get_tasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, name, description, course_id FROM tasks"; // SQL-запрос для получения id, name, description и course_id задач
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int course_id = resultSet.getInt("course_id"); // Получаем course_id из результата
    
                Course course = DataBase.get_course(course_id);
                Task task = new Task(id, name, description, course);
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks; 
    }

    public static List<Lesson> get_lessons() {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT id, course_id, date, time, users_id FROM lessons"; // SQL-запрос для получения всех уроков
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String users_id_str = resultSet.getString("users_id"); // Получаем users_id как строку
    
                Course course = DataBase.get_course(course_id);
                ArrayList<User> students = new ArrayList<>();
    
                if (users_id_str != null && !users_id_str.isEmpty()) {
                    users_id_str = users_id_str.replaceAll("[{}]", "").trim();
                    String[] userIds = users_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String userId : userIds) {
                        User user = DataBase.get_user(Integer.parseInt(userId.trim())); // Получаем объект User по userId
                        if (user != null) {
                            students.add(user); // Добавляем пользователя в список студентов
                        }
                    }
                }
                Lesson lesson = new Lesson(id, course, date, time, students);
                lessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons; 
    }

} 