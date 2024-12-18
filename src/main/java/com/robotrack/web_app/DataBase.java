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
        return false; // Если произошла ошибка или запись не найдена
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
            return false; // Возвращает false в случае ошибки
        }
    }

    public static List<User> get_users(){
        List<User> users = new ArrayList<>();
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password, role_id FROM users"; // Предполагается, что role_id хранится в базе данных

        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
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
                User user = new User(id, firstName, patronymic, lastName, birthDate, phoneNumber, password, role);
                users.add(user); // Добавляем пользователя в список
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users; 
    }

    public static User get_user(int user_id) {
        User user = null; // Изначально пользователь равен null
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password, role_id FROM users WHERE id = ?"; // SQL-запрос для получения пользователя по id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем значение user_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String lastName = resultSet.getString("last_name");
                String birthDate = resultSet.getString("birth_date");
                String phoneNumber = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id"); // Получаем role_id из результата
    
                // Создаем объект Role на основе roleId
                Role role = DataBase.get_role(roleId); // Предполагается, что у вас есть соответствующий конструктор в классе Role
    
                // Создаем объект User
                user = new User(id, firstName, patronymic, lastName, birthDate, phoneNumber, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
        
        return user; // Возвращаем найденного пользователя или null, если не найден
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
                String name = resultSet.getString("name"); // Получаем имя роли
    
                // Создаем объект Role
                role = new Role(role_id, name); // Используем конструктор Role с id и name
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
        
        return role; // Возвращаем найденную роль или null, если не найдена
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
                String ageLimit = resultSet.getString("age_limit");
    
                // Создаем объект Course
                Course course = new Course(id, name, ageLimit); // Используем конструктор Course с name и age_limit
    
                courses.add(course); // Добавляем курс в список
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
                String ageLimit = resultSet.getString("age_limit");
    
                // Создаем объект Course
                course = new Course(id, name, ageLimit); // Используем конструктор Course с id, name и age_limit
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
        
        return course; // Возвращаем найденный курс или null, если не найден
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
                int courseId = resultSet.getInt("course_id"); // Получаем course_id из результата
    
                // Получаем объект Course по courseId
                Course course = DataBase.get_course(courseId); // Предполагается, что у вас есть соответствующий конструктор в классе Course
    
                // Создаем объект Task
                Task task = new Task(id, name, description, course);
    
                tasks.add(task); // Добавляем задачу в список
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
                int courseId = resultSet.getInt("course_id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String usersIdString = resultSet.getString("users_id"); // Получаем users_id как строку
    
                // Получаем объект Course по courseId
                Course course = DataBase.get_course(courseId); // Используем метод get_course для получения курса
    
                // Создаем список студентов
                ArrayList<User> students = new ArrayList<>(); // Изменено на ArrayList<User>
    
                // Проверяем, есть ли users_id
                if (usersIdString != null && !usersIdString.isEmpty()) {
                    usersIdString = usersIdString.replaceAll("[{}]", "").trim();
                    String[] userIds = usersIdString.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String userId : userIds) {
                        User user = DataBase.get_user(Integer.parseInt(userId.trim())); // Получаем объект User по userId
                        if (user != null) {
                            students.add(user); // Добавляем пользователя в список студентов
                        }
                    }
                }
    
                // Создаем объект Lesson
                Lesson lesson = new Lesson(id, course, date, time, students); // Передаем список студентов
    
                lessons.add(lesson); // Добавляем урок в список
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons; 
    }

} 