CREATE TABLE levels
(
    level_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    deleted     BOOLEAN DEFAULT FALSE
);

CREATE TABLE users
(
    user_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(255) NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARBINARY(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    role       VARCHAR(255) NOT NULL,
    deleted    BOOLEAN DEFAULT FALSE
);

CREATE TABLE rankings
(
    ranking_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    ranking       BIGINT    NOT NULL,
    previous_rank BIGINT,
    points        FLOAT     NOT NULL,
    user_id       BIGINT    NOT NULL UNIQUE,
    last_update   TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE logs
(
    log_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id  BIGINT       NOT NULL,
    log_date TIMESTAMP    NOT NULL,
    content  VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE subjects
(
    subject_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    deleted     BOOLEAN DEFAULT FALSE
);

CREATE TABLE materials
(
    material_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id  BIGINT       NOT NULL,
    level_id    BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    pdf_url     VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    completed   BOOLEAN DEFAULT FALSE,
    deleted     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id),
    FOREIGN KEY (level_id) REFERENCES levels (level_id)
);

CREATE TABLE flashcards
(
    flashcard_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id      BIGINT       NOT NULL,
    subject_id   BIGINT       NOT NULL,
    front_text   VARCHAR(255) NOT NULL,
    back_text    VARCHAR(255) NOT NULL,
    deleted      BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);

CREATE TABLE diagnostic_test_templates
(
    diagnostic_test_template_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id                  BIGINT       NOT NULL,
    test_name                   VARCHAR(255) NOT NULL,
    deleted                     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);

CREATE TABLE diagnostic_test_results
(
    test_result_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id          BIGINT    NOT NULL,
    test_template_id BIGINT    NOT NULL,
    date_taken       TIMESTAMP NOT NULL,
    score            FLOAT     NOT NULL,
    previous_score   FLOAT,
    deleted          BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (test_template_id) REFERENCES diagnostic_test_templates (diagnostic_test_template_id)
);

CREATE TABLE checking_tests
(
    checking_test_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id          BIGINT    NOT NULL,
    subject_id       BIGINT    NOT NULL,
    test_date        TIMESTAMP NOT NULL,
    score            FLOAT     NOT NULL,
    previous_score   FLOAT,
    deleted          BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);

CREATE TABLE quiz_rooms
(
    quiz_room_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255)        NOT NULL,
    access_code  VARCHAR(255) UNIQUE NOT NULL,
    created_at   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    started_at   TIMESTAMP,
    ended_at     TIMESTAMP,
    completed    BOOLEAN                      DEFAULT FALSE,
    status       VARCHAR(255)                 DEFAULT 'CREATED'
);

CREATE TABLE quiz_participants
(
    participant_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quiz_room_id   BIGINT NOT NULL,
    user_id        BIGINT NOT NULL,
    score          FLOAT     DEFAULT 0,
    joined_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed      BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (quiz_room_id) REFERENCES quiz_rooms (quiz_room_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    UNIQUE (quiz_room_id, user_id)
);

CREATE TABLE questions
(
    question_id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_text               VARCHAR(255) NOT NULL,
    answer_a                    VARCHAR(255) NOT NULL,
    answer_b                    VARCHAR(255) NOT NULL,
    answer_c                    VARCHAR(255) NOT NULL,
    answer_d                    VARCHAR(255) NOT NULL,
    correct_answer              VARCHAR(255) NOT NULL,
    checking_test_id            BIGINT,
    diagnostic_test_template_id BIGINT,
    quiz_room_id                BIGINT,
    deleted                     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (checking_test_id) REFERENCES checking_tests (checking_test_id),
    FOREIGN KEY (diagnostic_test_template_id) REFERENCES diagnostic_test_templates (diagnostic_test_template_id),
    FOREIGN KEY (quiz_room_id) REFERENCES quiz_rooms (quiz_room_id)
);

CREATE TABLE quiz_answers
(
    quiz_answer_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_id     BIGINT       NOT NULL,
    user_id         BIGINT       NOT NULL,
    selected_answer VARCHAR(255) NOT NULL,
    correct         BOOLEAN      NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions (question_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    UNIQUE (question_id, user_id)
);

CREATE TABLE answers
(
    answer_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    user_answer VARCHAR(255) NOT NULL,
    correct     BOOLEAN      NOT NULL,
    deleted     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES questions (question_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE points
(
    point_id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    checking_test_id BIGINT,
    user_id          BIGINT NOT NULL,
    material_id      BIGINT,
    deleted          BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (checking_test_id) REFERENCES checking_tests (checking_test_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (material_id) REFERENCES materials (material_id)
);

CREATE TABLE progress_trackings
(
    progress_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id          BIGINT    NOT NULL,
    material_id      BIGINT,
    checking_test_id BIGINT,
    test_result_id   BIGINT,
    progress_value   FLOAT     NOT NULL,
    updated_at       TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (material_id) REFERENCES materials (material_id),
    FOREIGN KEY (checking_test_id) REFERENCES checking_tests (checking_test_id),
    FOREIGN KEY (test_result_id) REFERENCES diagnostic_test_results (test_result_id)
);

CREATE TABLE user_subject_levels
(
    user_subject_level_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id               BIGINT NOT NULL,
    subject_id            BIGINT NOT NULL,
    level_id              BIGINT NOT NULL,
    deleted               BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id),
    FOREIGN KEY (level_id) REFERENCES levels (level_id)
);

INSERT INTO levels (name, description, deleted)
VALUES ('Beginner', 'Initial level for new users', false),
('Intermediate', 'Level for users with a little know how', false),
('Advanced', 'Level for advanced users', false);
INSERT INTO users (username, email, password, created_at, role, deleted)
VALUES ('john_doe', 'john.doe@example.com', '$2a$12$.JsfzVLClModtGKO0De7rOOGdoikYhfHRFjZvzmXmgbB83CPs99q2',
        '2024-11-19', 'ADMIN', false);
INSERT INTO rankings (ranking, previous_rank, points, user_id, last_update)
VALUES (1, 2, 1500.5, 1, '2024-11-19 12:00:00');
INSERT INTO logs (user_id, log_date, content)
VALUES (1, '2024-11-19 12:00:00', 'User got Beginner level');
INSERT INTO subjects (name, description, deleted)
VALUES ('Mathematics', 'Math course content', false);
INSERT INTO materials (subject_id, level_id, title, pdf_url, created_at, completed, deleted)
VALUES (1, 1, 'Algebra Basics', 'https://www.cimat.mx/ciencia_para_jovenes/bachillerato/libros/algebra_gelfand.pdf',
        '2024-11-01', false, false);
INSERT INTO flashcards (user_id, subject_id, front_text, back_text, deleted)
VALUES (1, 1, 'What is 2 + 2?', '4', false);
INSERT INTO diagnostic_test_templates (subject_id, test_name, deleted)
VALUES (1, 'Basic Math Test', FALSE);
INSERT INTO diagnostic_test_results (user_id, test_template_id, date_taken, score, previous_score, deleted)
VALUES (1, 1, '2024-11-27 10:00:00', 85.5, NULL, FALSE);
INSERT INTO checking_tests (user_id, subject_id, test_date, score, previous_score, deleted)
VALUES (1, 1, '2024-11-15', 90.0, 85.0, false);
INSERT INTO quiz_rooms (name, access_code, started_at, ended_at, completed, status)
VALUES ('Math Quiz Room', 'ABC123', NULL, NULL, FALSE, 'CREATED');
INSERT INTO quiz_participants (quiz_room_id, user_id, score, completed)
VALUES (1, 1, 0, FALSE);
INSERT INTO questions (question_text, answer_a, answer_b, answer_c, answer_d, correct_answer, checking_test_id,
                       diagnostic_test_template_id,
                       quiz_room_id, deleted)
VALUES ('1','2', '3', '4', '5', '4', null, 1, null, false);
INSERT INTO quiz_answers (question_id, user_id, selected_answer, correct)
VALUES (1, 1, '4', TRUE);
INSERT INTO answers (question_id, user_id, user_answer, correct, deleted)
VALUES (1, 1, '4', true, false);
INSERT INTO points (checking_test_id, user_id, material_id, deleted)
VALUES (1, 1, 1, false);
INSERT INTO progress_trackings (user_id, material_id, checking_test_id, test_result_id, progress_value, updated_at)
VALUES (1, null, null, 1, 75.5, '2024-11-18');
INSERT INTO user_subject_levels (user_id, subject_id, level_id, deleted)
VALUES (1, 1, 1, FALSE)
