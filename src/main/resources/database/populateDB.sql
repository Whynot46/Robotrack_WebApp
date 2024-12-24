-- Вставка ролей
INSERT INTO roles (name) VALUES
('STUDENT'),
('PARENT'),
('TEACHER'),
('ADMIN');

-- Вставка курсов
INSERT INTO courses (name, age_limit) VALUES
('Робототехника 4-6 лет', '4-6 лет'),
('Робототехника 7-9 лет', '7-9 лет'),
('Робототехника 10-14 лет', '10-14 лет'),
('Робототехника 14+ лет', '14+ лет');

-- Вставка пользователей
INSERT INTO users (first_name, patronymic, last_name, birth_date, phone_number, password_hash, role_id) VALUES
('Иван', 'Иванович', 'Иванов', '2010-05-15', '+79991234567', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 1),
('Мария', 'Петровна', 'Петрова', '2011-08-20', '+79991234568', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 1),
('Алексей', 'Сергеевич', 'Сергеев', '2008-03-10', '+79991234569', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 1),
('Ольга', 'Александровна', 'Александрова', '2009-12-30', '+79991234570', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 1),
('Анна', 'Викторовна', 'Викторова', '2007-11-25', '+79991234571', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 1),
('Сергей', 'Николаевич', 'Николаев', '1985-02-14', '+79991234572', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 4),
('Елена', 'Анатольевна', 'Анатольева', '1980-07-22', '+79991234573', '$2a$10$zkiBQmq5Ih3bZ4Y5UYWq8eiCsWoF.f29Tf0r3ymX9ZNl3UvTvWL.m', 2);

-- Вставка заданий
INSERT INTO tasks (name, description, course_id) VALUES
('Собрать простую модель робота', 'Используя конструктор, собрать модель робота.', 1),
('Написать программу для движения', 'Создать программу, которая заставит робота двигаться вперед.', 2),
('Изучить основы механики', 'Понять основные принципы механики и их применение в робототехнике.', 3),
('Участвовать в соревнованиях', 'Подготовить робота для участия в соревнованиях.', 4);

-- Вставка занятий
INSERT INTO lessons (course_id, date, time, users_id) VALUES
(1, '2023-09-01', '10:00:00', ARRAY[1, 2]),
(2, '2023-09-02', '11:00:00', ARRAY[1, 3]),
(3, '2023-09-03', '12:00:00', ARRAY[2, 4]),
(4, '2023-09-04', '13:00:00', ARRAY[1, 3, 5]); 

-- Заполнение таблицы student_profile
INSERT INTO student_profile (user_id, school_shift, last_task_id, courses_id, lessons_id) VALUES
(3, 1, 2, ARRAY[1, 2], ARRAY[1]), -- Сергей
(5, 1, 1, ARRAY[1, 3], ARRAY[1]); -- Олег

-- Заполнение таблицы parent_profile
INSERT INTO parent_profile (user_id, child_id) VALUES
(4, 3), -- Родитель Анны за Сергея
(4, 5); -- Родитель Анны за Олега

-- Заполнение таблицы teacher_profile
INSERT INTO teacher_profile (user_id, courses_id, lessons_id) VALUES
(2, ARRAY[1, 2, 3], ARRAY[1, 2, 3]); -- Учитель Мария

-- Заполнение таблицы admin_profile
INSERT INTO admin_profile (id) VALUES
(1); -- Администратор Иванов

-- Заполнение таблицы news
INSERT INTO news (id, author_id, title, content, publish_datetime) VALUES
(1, 1, 'Новый курс по робототехнике', 'Запись на новый курс по основам робототехники открыта!', '2023-10-01 09:00:00');