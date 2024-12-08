-- Добавление ролей в таблицу roles
INSERT INTO roles (name) VALUES
('Администратор'),
('Преподаватель'),
('Ученик'),
('Родитель');

-- Добавление первых пользователей в таблицу users
INSERT INTO users (first_name, patronymic, last_name, birth_date, phone_number, password, role_id) VALUES
('Алексей', 'Дмитриевич', 'Пахалев', '2004-11-28', '+79170960237', '1234', (SELECT id FROM roles WHERE name = 'Администратор')),
('Дарья', 'Борисовна', 'Пахалева', '2005-01-03', '+79171703575', '1234', (SELECT id FROM roles WHERE name = 'Ученик'));