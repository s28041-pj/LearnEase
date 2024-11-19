CREATE TABLE levels
(
    level_id    INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    deleted     BOOLEAN
);

CREATE TABLE users
(
    user_id    INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(255) UNIQUE,
    email      VARCHAR(255) UNIQUE,
    password   VARBINARY(255),
    level_id   INT,
    created_at DATE,
    role       VARCHAR(255),
    deleted    BOOLEAN,
    FOREIGN KEY (level_id) REFERENCES levels (level_id)
);

CREATE TABLE rankings
(
    ranking_id    INT PRIMARY KEY AUTO_INCREMENT,
    ranking          INT,
    previous_rank INT,
    points        FLOAT,
    user_id       INT UNIQUE,
    last_update   TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE logs
(
    log_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_id  INT,
    log_date TIMESTAMP,
    content  VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE subjects
(
    subject_id  INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    deleted     BOOLEAN
);

CREATE TABLE materials
(
    material_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id  INT,
    level_id    INT,
    title       VARCHAR(255),
    pdf_url     VARCHAR(255),
    created_at  DATE,
    completed   BOOLEAN,
    deleted     BOOLEAN,
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id),
    FOREIGN KEY (level_id) REFERENCES levels (level_id)
);

CREATE TABLE flashcards
(
    flashcard_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT,
    subject_id   INT,
    front_text   VARCHAR(255),
    back_text    VARCHAR(255),
    deleted      BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);

CREATE TABLE diagnostic_tests
(
    diagnostic_test_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id            INT,
    subject_id         INT,
    date_taken         DATE,
    score              FLOAT,
    previous_score     FLOAT,
    deleted            BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);

CREATE TABLE checking_tests
(
    checking_test_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id          INT,
    subject_id       INT,
    test_date        DATE,
    score            FLOAT,
    previous_score   FLOAT,
    deleted          BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);

CREATE TABLE questions
(
    question_id        INT PRIMARY KEY AUTO_INCREMENT,
    answer_a           VARCHAR(255),
    answer_b           VARCHAR(255),
    answer_c           VARCHAR(255),
    answer_d           VARCHAR(255),
    correct_answer     VARCHAR(255),
    checking_test_id   INT,
    diagnostic_test_id INT,
    deleted            BOOLEAN,
    FOREIGN KEY (checking_test_id) REFERENCES checking_tests (checking_test_id),
    FOREIGN KEY (diagnostic_test_id) REFERENCES diagnostic_tests (diagnostic_test_id)
);

CREATE TABLE answers
(
    answer_id   INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT,
    user_id     INT,
    user_answer VARCHAR(255),
    correct     BOOLEAN,
    deleted     BOOLEAN,
    FOREIGN KEY (question_id) REFERENCES questions (question_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE points
(
    point_id         INT PRIMARY KEY AUTO_INCREMENT,
    checking_test_id INT,
    user_id          INT,
    material_id      INT,
    deleted          BOOLEAN,
    FOREIGN KEY (checking_test_id) REFERENCES checking_tests (checking_test_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (material_id) REFERENCES materials (material_id)
);

CREATE TABLE progress_trackings
(
    progress_id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id            INT,
    material_id        INT,
    checking_test_id   INT,
    diagnostic_test_id INT,
    progress_value     FLOAT,
    updated_at         DATE,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (material_id) REFERENCES materials (material_id),
    FOREIGN KEY (checking_test_id) REFERENCES checking_tests (checking_test_id),
    FOREIGN KEY (diagnostic_test_id) REFERENCES diagnostic_tests (diagnostic_test_id)
);

INSERT INTO levels (name, description, deleted) VALUES ('Beginner', 'Initial level for new users', false);
INSERT INTO users (username, email, password, level_id, created_at, role, deleted) VALUES ('john_doe', 'john.doe@example.com', '$2a$12$.JsfzVLClModtGKO0De7rOOGdoikYhfHRFjZvzmXmgbB83CPs99q2', 1, '2024-11-19', 'ADMIN', false);
INSERT INTO rankings (ranking, previous_rank, points, user_id, last_update) VALUES (1, 2, 1500.5, 1, '2024-11-19 12:00:00');
INSERT INTO logs (user_id, log_date, content) VALUES (1, '2024-11-19 12:00:00', 'User got Beginner level');
INSERT INTO subjects (name, description, deleted) VALUES ('Mathematics', 'Math course content', false);
INSERT INTO materials (subject_id, level_id, title, pdf_url, created_at, completed, deleted) VALUES (1, 1, 'Algebra Basics', 'https://www.cimat.mx/ciencia_para_jovenes/bachillerato/libros/algebra_gelfand.pdf', '2024-11-01', false, false);
INSERT INTO flashcards (user_id, subject_id, front_text, back_text, deleted) VALUES (1, 1, 'What is 2 + 2?', '4', false);
INSERT INTO diagnostic_tests (user_id, subject_id, date_taken, score, previous_score, deleted) VALUES (1, 1, '2024-11-10', 85.0, 80.0, false);
INSERT INTO checking_tests (user_id, subject_id, test_date, score, previous_score, deleted) VALUES (1, 1, '2024-11-15', 90.0, 85.0, false);
INSERT INTO questions (answer_a, answer_b, answer_c, answer_d, correct_answer, checking_test_id, diagnostic_test_id, deleted) VALUES ('2', '3', '4', '5', '4', 1, 1, false);
INSERT INTO answers (question_id, user_id, user_answer, correct, deleted) VALUES (1, 1, '4', true, false);
INSERT INTO points (checking_test_id, user_id, material_id, deleted) VALUES (1, 1, 1, false);
INSERT INTO progress_trackings (user_id, material_id, checking_test_id, diagnostic_test_id, progress_value, updated_at) VALUES (1, 1, 1, 1, 75.5, '2024-11-18');

