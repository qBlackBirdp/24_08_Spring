#String url = "jdbc:mysql://127.0.0.1:3306/24_08_Spring?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
#String user = "root";
#String pass = "1234";
#Class.forName("org.mariadb.jdbc.Driver");


DROP DATABASE IF EXISTS `24_08_Spring`;
CREATE DATABASE `24_08_Spring`;
USE `24_08_Spring`;

SHOW TABLES;

CREATE TABLE article(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        title CHAR(100) NOT NULL,
                        `body` TEXT NOT NULL
);

ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

SELECT *
FROM article;

CREATE TABLE `member`
(
    id         int(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate    DATETIME         NOT NULL,
    updateDate DATETIME         NOT NULL,
    loginId   char(30)        not null,
    loginPw   char(100)        not null,
    `name`      char(100)        NOT NULL,
    nickname	char(20)		NOT NULL,
    cellphoneNum char(20) 		NOT NULL,
	email 		char(100)		NOT NULL
);

SELECT *
FROM `member`;


##############################   TEST   ####################################
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    memberId = 2;

SELECT *
FROM article;

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test1',
    loginPw = 'test1',
    `name` = '김철수',
    nickname = '김철수 닉네임',
    cellphoneNum = '01011312242',
    email = 'abcddd@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test2',
    loginPw = 'test2',
    `name` = '홍길동',
    nickname = '홍길동 닉네임',
    cellphoneNum = '01011112222',
    email = 'abc@gmail.com';



SELECT a.*, m.id
FROM article a
         INNER JOIN `member` m
                    ON a.memberId = m.id
WHERE m.id = 3;

UPDATE article a
    INNER JOIN `member` m
SET a.updateDate = NOW(),
    a.title = '2222',
    a.`body`= '2222'
WHERE a.id = 1 AND m.id = 1;
