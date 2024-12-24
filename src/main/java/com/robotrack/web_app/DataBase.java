package com.robotrack.web_app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    
    public static void add_user(String firstName, String patronymic, String lastName, String birthDate, String phoneNumber, String password, int role_id) {
        String query = "INSERT INTO users (first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password_hash = passwordEncoder.encode(password);
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, patronymic);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, birthDate);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, password_hash);
            preparedStatement.setInt(7, role_id);
            
            preparedStatement.executeUpdate(); // Выполняем обновление
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void add_lesson(int id, int course_id, String date, String time, ArrayList<Integer> students_id) {
        String query = "INSERT INTO lessons (id, course_id, date, time, users_id) VALUES (?, ?, ?, ?, ?)";
        
        // Преобразуем список идентификаторов студентов в строку для хранения в базе данных
        String studentsIdStr = students_id.toString(); // Пример: [1, 2, 3]
        studentsIdStr = studentsIdStr.substring(1, studentsIdStr.length() - 1); // Убираем квадратные скобки
    
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, course_id);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, time);
            preparedStatement.setString(5, studentsIdStr); // Сохраняем идентификаторы студентов как строку
            
            preparedStatement.executeUpdate(); // Выполняем обновление
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void add_course(int id, String name, String age_limit) {
        String query = "INSERT INTO courses (id, name, age_limit) VALUES (?, ?, ?)";
    
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, age_limit);
            
            preparedStatement.executeUpdate(); // Выполняем обновление
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void add_role(int id, String name) {
        String query = "INSERT INTO roles (id, name) VALUES (?, ?)";
    
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            
            preparedStatement.executeUpdate(); // Выполняем обновление
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void add_task(int id, String name, String description, int course_id) {
        String query = "INSERT INTO tasks (id, name, description, course_id) VALUES (?, ?, ?, ?)";
    
        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, course_id);
            
            preparedStatement.executeUpdate(); // Выполняем обновление
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void add_new(int id, int author_id, String title, String content, String publish_datetime) {
        String query = "INSERT INTO news (id, author_id, title, content, publish_datetime) VALUES (?, ?, ?, ?, ?)"; // SQL-запрос для добавления новой записи
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id); // Устанавливаем id
            preparedStatement.setInt(2, author_id); // Устанавливаем author_id
            preparedStatement.setString(3, title); // Устанавливаем title
            preparedStatement.setString(4, content); // Устанавливаем content
            preparedStatement.setString(5, publish_datetime); // Устанавливаем publish_datetime
    
            // Выполняем добавление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Новая запись успешно добавлена в таблицу news");
            } else {
                System.out.println("Не удалось добавить новую запись в таблицу news");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static List<User> get_users(){
        List<User> users = new ArrayList<>();
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id FROM users"; // Предполагается, что role_id хранится в базе данных

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
                String password_hash = resultSet.getString("password_hash");
                int role_id = resultSet.getInt("role_id");

                User user = new User(id, first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users; 
    }

    public static User get_user(int user_id) {
        User user = null;
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id FROM users WHERE id = ?"; // SQL-запрос для получения пользователя по id
    
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
                String password_hash = resultSet.getString("password_hash");
                int role_id = resultSet.getInt("role_id"); // Получаем role_id из результата
    
                user = new User(id, first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    public static User get_user(String phone_number) {
        String query = "SELECT id, first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id FROM users WHERE phone_number = ?"; // SQL-запрос для получения пользователя по номеру телефона
        User user = null; // Изначально пользователь равен null
        System.out.println("Phone number fron DB.get_user:" + phone_number);

        try (Connection connection = getConnection(); // Получаем соединение из пула
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, phone_number); // Устанавливаем номер телефона в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос

            if (resultSet.next()) { // Если запись найдена
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String last_name = resultSet.getString("last_name");
                String birth_date = resultSet.getString("birth_date");
                String phone_number_db = resultSet.getString("phone_number");
                String password_hash = resultSet.getString("password_hash");
                int role_id = resultSet.getInt("role_id");

                // Создаем объект User с полученными данными
                user = new User(id, first_name, patronymic, last_name, birth_date, phone_number_db, password_hash, role_id);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }

        return user; // Возвращаем объект User или null, если не найден
    }

    public static ArrayList<New> get_news() {
        ArrayList<New> news_list = new ArrayList<>(); // Список для хранения новостей
        String query = "SELECT id, author_id, title, content, publish_datetime FROM news"; // SQL-запрос для получения всех новостей
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) { // Обрабатываем все результаты
                int id = resultSet.getInt("id");
                int author_id = resultSet.getInt("author_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String publish_datetime = resultSet.getString("publish_datetime");
    
                // Создаем объект New и добавляем его в список
                New news_obj = new New(id, author_id, title, content, publish_datetime);
                news_list.add(news_obj);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return news_list; // Возвращаем список новостей
    }

    public static New get_new(int new_id) {
        New news_obj = null; // Изначально объект новости равен null
        String query = "SELECT id, author_id, title, content, publish_datetime FROM news WHERE id = ?"; // SQL-запрос для получения новости по id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, new_id); // Устанавливаем new_id для запроса
    
            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) { // Если запись найдена
                int id = resultSet.getInt("id");
                int author_id = resultSet.getInt("author_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String publish_datetime = resultSet.getString("publish_datetime");
    
                // Создаем объект New с полученными данными
                news_obj = new New(id, author_id, title, content, publish_datetime);
            } else {
                System.out.println("Новость с id=" + new_id + " не найдена.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return news_obj; // Возвращаем объект новости или null, если не найдено
    }

    public static ArrayList<New> get_news_from_user(int user_id) {
        ArrayList<New> news_list = new ArrayList<>(); // Список для хранения новостей пользователя
        String query = "SELECT id, author_id, title, content, publish_datetime FROM news WHERE author_id = ?"; // SQL-запрос для получения новостей по author_id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем user_id для запроса
    
            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) { // Обрабатываем все результаты
                int id = resultSet.getInt("id");
                int author_id = resultSet.getInt("author_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String publish_datetime = resultSet.getString("publish_datetime");
    
                // Создаем объект New и добавляем его в список
                New newsItem = new New(id, author_id, title, content, publish_datetime);
                news_list.add(newsItem);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return news_list; // Возвращаем список новостей пользователя
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

    public static List<Course> get_courses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT id, name, age_limit, tasks_id FROM courses"; // SQL-запрос для получения id, name, age_limit и tasks_id курсов
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age_limit = resultSet.getString("age_limit");
                String tasks_id_str = resultSet.getString("tasks_id"); // Извлекаем tasks_id как строку
                
                // Преобразуем строку tasks_id в список идентификаторов задач
                ArrayList<Integer> tasks_id = new ArrayList<>();
                if (tasks_id_str != null && !tasks_id_str.isEmpty()) {
                    String[] ids = tasks_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String task_id : ids) {
                        tasks_id.add(Integer.parseInt(task_id.trim())); // Добавляем идентификатор задачи в список
                    }
                }
    
                Course course = new Course(id, name, age_limit, tasks_id);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses; 
    }

    public static Course get_course(int course_id) {
        Course course = null; // Изначально курс равен null
        String query = "SELECT id, name, age_limit, tasks_id FROM courses WHERE id = ?"; // SQL-запрос для получения курса по id
        
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                
            preparedStatement.setInt(1, course_id); // Устанавливаем значение course_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
        
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age_limit = resultSet.getString("age_limit");
                String tasks_id_str = resultSet.getString("tasks_id"); // Извлекаем tasks_id как строку
                
                // Преобразуем строку tasks_id в список идентификаторов задач
                ArrayList<Integer> tasks_id = new ArrayList<>();
                if (tasks_id_str != null && !tasks_id_str.isEmpty()) {
                    String[] ids = tasks_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String task_id : ids) {
                        tasks_id.add(Integer.parseInt(task_id.trim())); // Добавляем идентификатор задачи в список
                    }
                }
                
                course = new Course(id, name, age_limit, tasks_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
        return course;
    }
 
    public static List<Task> get_tasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, name, description FROM tasks"; // SQL-запрос для получения id, name, description и course_id задач
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
    
                Task task = new Task(id, name, description);
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks; 
    }

    public static int get_student_last_task_id(int student_id) {
        int lastTaskId = -1; // Изначально lastTaskId равен -1, что будет означать, что задача не найдена
        String query = "SELECT last_task_id FROM student_profiles WHERE user_id = ?"; // SQL-запрос для получения last_task_id студента
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, student_id); // Устанавливаем значение student_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                lastTaskId = resultSet.getInt("last_task_id"); // Получаем last_task_id из результата
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return lastTaskId; // Возвращаем lastTaskId (или -1, если задача не найдена)
    }

    public static List<Lesson> get_lessons() {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT id, course_id, date, time, students_id FROM lessons"; // SQL-запрос для получения всех уроков
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Извлекаем данные из результата
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String students_id_str = resultSet.getString("students_id"); // Получаем students_id как строку
    
                // Преобразуем строку students_id в список идентификаторов студентов
                ArrayList<Integer> students_id = new ArrayList<>();
                if (students_id_str != null && !students_id_str.isEmpty()) {
                    String[] ids = students_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String studentId : ids) {
                        students_id.add(Integer.parseInt(studentId.trim())); // Добавляем идентификатор студента в список
                    }
                }
    
                // Создаем объект Lesson и добавляем его в список
                Lesson lesson = new Lesson(id, course_id, date, time, students_id);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
        return lessons; 
    }

    public static StudentProfile get_student_profile(int user_id) {
        StudentProfile student_profile = null; // Изначально профиль студента равен null
        String query = "SELECT * FROM student_profiles WHERE user_id = ?"; // SQL-запрос для получения профиля студента по user_id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем значение user_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                String school_shift = resultSet.getString("school_shift"); // Получаем значение school_shift
                int last_task_id = resultSet.getInt("last_task_id"); // Получаем last_task_id
                ArrayList<Integer> courses_id = new ArrayList<>(); // Список для хранения идентификаторов курсов
                ArrayList<Integer> lessons_id = new ArrayList<>(); // Список для хранения идентификаторов занятий
    
                // Получаем идентификаторы курсов и занятий (предполагается, что они хранятся в виде строк, разделенных запятыми)
                String courses_id_str = resultSet.getString("courses_id");
                String lessons_id_str = resultSet.getString("lessons_id");
    
                // Преобразуем строку идентификаторов курсов в список
                if (courses_id_str != null && !courses_id_str.isEmpty()) {
                    String[] ids = courses_id_str.split(","); // Разделяем строку по запятой
                    for (String id : ids) {
                        courses_id.add(Integer.parseInt(id.trim())); // Добавляем идентификатор курса в список
                    }
                }
    
                // Преобразуем строку идентификаторов занятий в список
                if (lessons_id_str != null && !lessons_id_str.isEmpty()) {
                    String[] ids = lessons_id_str.split(","); // Разделяем строку по запятой
                    for (String id : ids) {
                        lessons_id.add(Integer.parseInt(id.trim())); // Добавляем идентификатор занятия в список
                    }
                }
    
                // Создаем объект StudentProfile с полученными данными
                student_profile = new StudentProfile(user_id, school_shift, last_task_id, courses_id, lessons_id);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return student_profile; // Возвращаем профиль студента
    }

    public static ParentProfile get_parent_profile(int user_id) {
        ParentProfile parent_profile = null; // Изначально профиль родителя равен null
        String query = "SELECT * FROM parent_profiles WHERE user_id = ?"; // SQL-запрос для получения профиля родителя по user_id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем значение user_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                int child_id = resultSet.getInt("child_id"); // Получаем идентификатор ребенка
                parent_profile = new ParentProfile(user_id, child_id); // Создаем объект ParentProfile с полученными данными
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return parent_profile; // Возвращаем профиль родителя
    }

    public static TeacherProfile get_teacher_profile(int user_id) {
        TeacherProfile teacher_profile = null; // Изначально профиль равен null
        String query = "SELECT user_id, courses_id FROM teacher_profiles WHERE user_id = ?"; // SQL-запрос для получения профиля учителя по user_id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем значение user_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("user_id");
                ArrayList<Integer> courses_id = new ArrayList<>(); // Список для хранения идентификаторов курсов
    
                // Получаем идентификаторы курсов из результата
                String courses_id_str = resultSet.getString("courses_id");
                if (courses_id_str != null && !courses_id_str.isEmpty()) {
                    String[] ids = courses_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String course_id : ids) {
                        courses_id.add(Integer.parseInt(course_id.trim())); // Добавляем идентификатор курса в список
                    }
                }
    
                teacher_profile = new TeacherProfile(id, courses_id); // Создаем объект TeacherProfile
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return teacher_profile; // Возвращаем профиль учителя
    }

    public static AdminProfile get_admin_profile(int user_id) {
        AdminProfile admin_profile = null; // Изначально профиль равен null
        String query = "SELECT user_id FROM admin_profiles WHERE user_id = ?"; // SQL-запрос для получения профиля администратора по user_id
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user_id); // Устанавливаем значение user_id в запрос
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("user_id");
                admin_profile = new AdminProfile(id); // Создаем объект AdminProfile
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return admin_profile; // Возвращаем профиль администратора
    }   

    public static void change_user_data(int id, String first_name, String patronymic, String last_name, String birth_date, String phone_number, String password, int role_id) {
        String query = "UPDATE users SET first_name = ?, patronymic = ?, last_name = ?, birth_date = ?, phone_number = ?, password_hash = ?, role_id = ? WHERE id = ?"; // SQL-запрос для обновления данных пользователя
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password_hash = passwordEncoder.encode(password);

        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            // Устанавливаем параметры запроса
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, patronymic);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, birth_date);
            preparedStatement.setString(5, phone_number);
            preparedStatement.setString(6, password_hash);
            preparedStatement.setInt(7, role_id);
            preparedStatement.setInt(8, id); // Устанавливаем id пользователя для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные пользователя успешно обновлены");
            } else {
                System.out.println("Пользователь с id="+ id +" не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_new_data(int id, int author_id, String title, String content, String publish_datetime) {
        String query = "UPDATE news SET author_id = ?, title = ?, content = ?, publish_datetime = ? WHERE id = ?"; // SQL-запрос для обновления данных новости
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, author_id); // Устанавливаем author_id
            preparedStatement.setString(2, title); // Устанавливаем title
            preparedStatement.setString(3, content); // Устанавливаем content
            preparedStatement.setString(4, publish_datetime); // Устанавливаем publish_datetime
            preparedStatement.setInt(5, id); // Устанавливаем id для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные новости с id=" + id + " успешно обновлены.");
            } else {
                System.out.println("Новость с id=" + id + " не найдена или данные не изменены.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_task_data(int id, String name, String description, int course_id) {
        String query = "UPDATE tasks SET name = ?, description = ?, course_id = ? WHERE id = ?"; // SQL-запрос для обновления данных задания
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            // Устанавливаем параметры запроса
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, course_id);
            preparedStatement.setInt(4, id); // Устанавливаем id задания для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные задания успешно обновлены");
            } else {
                System.out.println("Задание с id="+ id +" не найдено");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_course_data(int id, String name, String age_limit, ArrayList<Integer> tasks_id) {
    String query = "UPDATE courses SET name = ?, age_limit = ?, tasks_id = ? WHERE id = ?"; // SQL-запрос для обновления данных курса

    try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        // Устанавливаем параметры запроса
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, age_limit);
        
        // Преобразуем список tasks_id в строку для хранения в базе данных
        String tasks_id_str = tasks_id.stream()
                                       .map(String::valueOf)
                                       .collect(Collectors.joining(",")); // Преобразуем список в строку, разделенную запятыми
        preparedStatement.setString(3, tasks_id_str);
        preparedStatement.setInt(4, id); // Устанавливаем id курса для обновления

        // Выполняем обновление
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Данные курса успешно обновлены");
            } else {
                System.out.println("Курс с id="+ id +" не найден");
            }
    } catch (SQLException e) {
        e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_lesson_data(int id, int course_id, String date, String time, ArrayList<Integer> students_id) {
        String query = "UPDATE lessons SET course_id = ?, date = ?, time = ?, students_id = ? WHERE id = ?"; // SQL-запрос для обновления данных занятия
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            // Устанавливаем параметры запроса
            preparedStatement.setInt(1, course_id);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
            
            // Преобразуем список students_id в строку для хранения в базе данных
            String students_id_str = students_id.stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(",")); // Преобразуем список в строку, разделенную запятыми
            preparedStatement.setString(4, students_id_str);
            preparedStatement.setInt(5, id); // Устанавливаем id занятия для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные занятия успешно обновлены");
            } else {
                System.out.println("Занятие с id="+ id +" не найдено");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_student_profile(int user_id, String school_shift, int last_task_id, ArrayList<Integer> courses_id, ArrayList<Integer> lessons_id) {
        String query = "UPDATE student_profiles SET school_shift = ?, last_task_id = ?, courses_id = ?, lessons_id = ? WHERE user_id = ?"; // SQL-запрос для обновления данных профиля студента
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, school_shift);
            preparedStatement.setInt(2, last_task_id);
            
            // Преобразуем список идентификаторов курсов в строку для хранения в базе данных
            String courses_id_str = courses_id.stream()
                                             .map(String::valueOf)
                                             .collect(Collectors.joining(",")); // Преобразуем список в строку, разделенную запятыми
            preparedStatement.setString(3, courses_id_str);
            
            // Преобразуем список идентификаторов занятий в строку для хранения в базе данных
            String lessons_id_str = lessons_id.stream()
                                             .map(String::valueOf)
                                             .collect(Collectors.joining(",")); // Преобразуем список в строку, разделенную запятыми
            preparedStatement.setString(4, lessons_id_str);
            
            preparedStatement.setInt(5, user_id); // Устанавливаем user_id для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные профиля студента успешно обновлены");
            } else {
                System.out.println("Студент с id=" + user_id + " не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_parent_profile(int user_id, int child_id) {
        String query = "UPDATE parent_profiles SET child_id = ? WHERE user_id = ?"; // SQL-запрос для обновления данных профиля родителя
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, child_id);
            preparedStatement.setInt(2, user_id); // Устанавливаем user_id для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные профиля родителя успешно обновлены");
            } else {
                System.out.println("Родитель с id=" + user_id + " не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static void change_teacher_profile(int user_id, ArrayList<Integer> courses_id) {
        String query = "UPDATE teacher_profiles SET courses_id = ? WHERE user_id = ?"; // SQL-запрос для обновления данных профиля учителя
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Преобразуем список идентификаторов курсов в строку для хранения в базе данных
            String courses_id_str = courses_id.stream()
                                             .map(String::valueOf)
                                             .collect(Collectors.joining(",")); // Преобразуем список в строку, разделенную запятыми
            preparedStatement.setString(1, courses_id_str);
            preparedStatement.setInt(2, user_id); // Устанавливаем user_id для обновления
    
            // Выполняем обновление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные профиля учителя успешно обновлены");
            } else {
                System.out.println("Учитель с id=" + user_id + " не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    public static Course get_most_popular_course() {
        Course most_popular_course = null; // Изначально курс равен null
        String query = "SELECT c.id, c.name, c.age_limit, c.tasks_id, COUNT(ls.students_id) AS student_count " +
                       "FROM courses c " +
                       "LEFT JOIN lessons l ON c.id = l.course_id " +
                       "LEFT JOIN (SELECT unnest(string_to_array(students_id, ','))::int AS student_id FROM lessons) ls ON l.id = ls.lesson_id " +
                       "GROUP BY c.id " +
                       "ORDER BY student_count DESC " +
                       "LIMIT 1"; // SQL-запрос для получения самого популярного курса
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age_limit = resultSet.getString("age_limit");
                String tasks_id_str = resultSet.getString("tasks_id"); // Извлекаем tasks_id как строку
                
                // Преобразуем строку tasks_id в список идентификаторов задач
                ArrayList<Integer> tasks_id = new ArrayList<>();
                if (tasks_id_str != null && !tasks_id_str.isEmpty()) {
                    String[] ids = tasks_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String task_id : ids) {
                        tasks_id.add(Integer.parseInt(task_id.trim())); // Добавляем идентификатор задачи в список
                    }
                }
    
                most_popular_course = new Course(id, name, age_limit, tasks_id);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return most_popular_course; // Возвращаем самый популярный курс
    }

    public static Course get_most_unpopular_course() {
        Course most_unpopular_course = null; // Изначально курс равен null
        String query = "SELECT c.id, c.name, c.age_limit, c.tasks_id, COUNT(ls.students_id) AS student_count " +
                       "FROM courses c " +
                       "LEFT JOIN lessons l ON c.id = l.course_id " +
                       "LEFT JOIN (SELECT unnest(string_to_array(students_id, ','))::int AS student_id FROM lessons) ls ON l.id = ls.lesson_id " +
                       "GROUP BY c.id " +
                       "ORDER BY student_count ASC " + // Сортируем по количеству студентов в порядке возрастания
                       "LIMIT 1"; // SQL-запрос для получения самого непопулярного курса
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            if (resultSet.next()) { // Если есть результат
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age_limit = resultSet.getString("age_limit");
                String tasks_id_str = resultSet.getString("tasks_id"); // Извлекаем tasks_id как строку
                
                // Преобразуем строку tasks_id в список идентификаторов задач
                ArrayList<Integer> tasks_id = new ArrayList<>();
                if (tasks_id_str != null && !tasks_id_str.isEmpty()) {
                    String[] ids = tasks_id_str.split(","); // Предполагаем, что идентификаторы разделены запятыми
                    for (String task_id : ids) {
                        tasks_id.add(Integer.parseInt(task_id.trim())); // Добавляем идентификатор задачи в список
                    }
                }
    
                most_unpopular_course = new Course(id, name, age_limit, tasks_id);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return most_unpopular_course; // Возвращаем самый непопулярный курс
    }

    public static ArrayList<User> get_most_visiting_students() {
        ArrayList<User> most_visiting_students = new ArrayList<>(); // Список для хранения студентов
        String query = "SELECT u.id, u.first_name, u.patronymic, u.last_name, u.birth_date, u.phone_number, u.password, u.role_id, COUNT(ls.student_id) AS visit_count " +
                       "FROM users u " +
                       "JOIN (SELECT unnest(string_to_array(students_id, ','))::int AS student_id FROM lessons) ls ON u.id = ls.student_id " +
                       "WHERE u.role_id = (SELECT id FROM roles WHERE name = 'student') " + // Предполагаем, что у студентов есть роль с именем 'student'
                       "GROUP BY u.id " +
                       "ORDER BY visit_count DESC " + // Сортируем по количеству посещений в порядке убывания
                       "LIMIT 5"; // Ограничиваем результат до 5 студентов
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) { // Обрабатываем все результаты
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String last_name = resultSet.getString("last_name");
                String birthdate = resultSet.getString("birth_date");
                String phone_number = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                int role_id = resultSet.getInt("role_id");
    
                User student = new User(id, first_name, patronymic, last_name, birthdate, phone_number, password, role_id);
                most_visiting_students.add(student); // Добавляем студента в список
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return most_visiting_students; // Возвращаем список студентов
    }

    public static ArrayList<User> get_most_unvisiting_students() {
        ArrayList<User> most_unvisiting_students = new ArrayList<>(); // Список для хранения студентов
        String query = "SELECT u.id, u.first_name, u.patronymic, u.last_name, u.birth_date, u.phone_number, u.password, u.role_id, COUNT(ls.student_id) AS visit_count " +
                       "FROM users u " +
                       "LEFT JOIN (SELECT unnest(string_to_array(students_id, ','))::int AS student_id FROM lessons) ls ON u.id = ls.student_id " +
                       "WHERE u.role_id = (SELECT id FROM roles WHERE name = 'student') " + // Предполагаем, что у студентов есть роль с именем 'student'
                       "GROUP BY u.id " +
                       "ORDER BY visit_count ASC " + // Сортируем по количеству посещений в порядке возрастания
                       "LIMIT 5"; // Ограничиваем результат до 5 студентов
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) { // Обрабатываем все результаты
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String last_name = resultSet.getString("last_name");
                String birthdate = resultSet.getString("birth_date");
                String phone_number = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                int role_id = resultSet.getInt("role_id");
    
                User student = new User(id, first_name, patronymic, last_name, birthdate, phone_number, password, role_id);
                most_unvisiting_students.add(student); // Добавляем студента в список
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return most_unvisiting_students; // Возвращаем список студентов
    }

    public static int get_all_visitor_quantity_by_month(int month, int year) {
        int visitor_сount = 0; // Изначально количество посетителей равно 0
        String query = "SELECT COUNT(ls.student_id) AS visitor_count " +
                       "FROM lessons l " +
                       "LEFT JOIN (SELECT unnest(string_to_array(students_id, ','))::int AS student_id FROM lessons) ls ON l.id = ls.lesson_id " +
                       "WHERE EXTRACT(MONTH FROM l.date) = ? AND EXTRACT(YEAR FROM l.date) = ?"; // SQL-запрос для подсчета посещений
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, month); // Устанавливаем месяц
            preparedStatement.setInt(2, year); // Устанавливаем год
            
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                visitor_сount = resultSet.getInt("visitor_count"); // Получаем количество посетителей
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return visitor_сount; // Возвращаем количество посетителей
    }

    public static int get_course_visitor_quantity_by_month(int month, int year) {
        int visitor_count = 0; // Изначально количество посетителей равно 0
        String query = "SELECT COUNT(ls.student_id) AS visitor_count " +
                       "FROM courses c " +
                       "JOIN lessons l ON c.id = l.course_id " +
                       "LEFT JOIN (SELECT unnest(string_to_array(students_id, ','))::int AS student_id FROM lessons) ls ON l.id = ls.lesson_id " +
                       "WHERE EXTRACT(MONTH FROM l.date) = ? AND EXTRACT(YEAR FROM l.date) = ?"; // SQL-запрос для подсчета посещений
    
        try (Connection connection = DataBase.getConnection(); // Получаем соединение из класса DataBase
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, month); // Устанавливаем месяц
            preparedStatement.setInt(2, year); // Устанавливаем год
            
            ResultSet resultSet = preparedStatement.executeQuery(); // Выполняем запрос
    
            if (resultSet.next()) { // Если есть результат
                visitor_count = resultSet.getInt("visitor_count"); // Получаем количество посетителей
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    
        return visitor_count; // Возвращаем количество посетителей
    }

} 