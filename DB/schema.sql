DROP DATABASE
IF EXISTS sbb;

CREATE DATABASE sbb;

CREATE TABLE Question (
    id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `subject` VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    create_date DATETIME NOT NULL,
    answer_id INT(11) UNSIGNED
);

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 1',
content = '질문내용 1',
answer_id = 1;

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 2',
content = '질문내용 2',
answer_id = 2;

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 3',
content = '질문내용 3',
answer_id = 3;

CREATE TABLE Answer (
    id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    create_date DATETIME NOT NULL,
    question_id INT(11) UNSIGNED NOT NULL
);

INSERT INTO Answer SET
create_date = NOW(),
content = '답변내용 1',
question_id = 1;

INSERT INTO Answer SET
create_date = NOW(),
content = '답변내용 2',
question_id = 2;

INSERT INTO Answer SET
create_date = NOW(),
content = '답변내용 3',
question_id = 3;

SELECT * FROM Question;
SELECT * FROM Answer;