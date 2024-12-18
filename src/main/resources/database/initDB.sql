CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    phone_number VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS courses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age_limit VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS lessons (
    id SERIAL PRIMARY KEY,
    course_id INT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    users_id INT[],
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS student_profile (
    user_id INT PRIMARY KEY,
    school_shift INT,
    last_task_id INT,
    courses_id INT[],
    lessons_id INT[],
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (last_task_id) REFERENCES tasks(id)
);

CREATE TABLE IF NOT EXISTS parent_profile (
    user_id INT,
    child_id INT,
    PRIMARY KEY (user_id, child_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (child_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS teacher_profile (
    user_id INT PRIMARY KEY,
    courses_id INT[],
    lessons_id INT[],
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS admin_profile (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id)
);