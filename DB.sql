#String url = "jdbc:mysql://127.0.0.1:3306/24_08_Spring?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
#String user = "root";
#String pass = "1234";
#Class.forName("org.mariadb.jdbc.Driver");


DROP DATABASE IF EXISTS `24_08_Spring`;
CREATE DATABASE `24_08_Spring`;
USE `24_08_Spring`;

SHOW TABLES;

#게시물 테이블 생성
CREATE TABLE article(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        title CHAR(100) NOT NULL,
                        `body` TEXT NOT NULL
);

ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

SELECT *
FROM article;

# 회원 테이블 생성
CREATE TABLE `member`
(
    id         int(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate    DATETIME         NOT NULL,
    updateDate DATETIME         NOT NULL,
    loginId   char(30)        not null,
    loginPw   char(100)        not null,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨 (3=일반, 7=관리자)',
    `name`      char(100)        NOT NULL,
    nickname	char(20)		NOT NULL,
    cellphoneNum char(20) 		NOT NULL,
	email 		char(100)		NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부 (0=탈퇴 전, 1=탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴 날짜'
);

SELECT *
FROM `member`;

-- DELETE
-- From `member`
-- where id = 7;

#게시판 테이블 생성
CREATE TABLE board(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        `code` CHAR(50) NOT NULL Unique comment '공지사항, QnA 등',
                        `name` CHAR(50) NOT NULL Unique comment '',
						delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부 (0=탈퇴 전, 1=탈퇴 후)',
						delDate DATETIME COMMENT '탈퇴 날짜'
);

#좋아요 기능 테이블 생성
CREATE TABLE reactionPoint (
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL,
    likePoint INT(10) NOT NULL DEFAULT 0,
    unlikePoint INT(10) NOT NULL DEFAULT 0,
    UNIQUE KEY `unique_member_relTypeCode` (memberId, relId, relTypeCode)
);



##############################   TEST   ####################################
-- 좋아요 테스트 데이터.
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relId = 1,
relTypeCode = 'article', -- 예: 게시글에 대한 좋아요
likePoint = 1,
unlikePoint = 0;

INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relId = 1,
relTypeCode = 'article', -- 예: 게시글에 대한 싫어요
likePoint = 0,
unlikePoint = 1;

INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relId = 2,
relTypeCode = 'article', -- 예: 게시글에 대한 좋아요
likePoint = 1,
unlikePoint = 0;


-- 게시판 테스트 데이터.	
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free',
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'QnA',
`name` = '질의응답';

UPDATE article
SET boardId = 1
WHERE id IN (1,2, 3, 4);

UPDATE article
SET boardId = 2
WHERE id in (5, 6);

UPDATE article
SET boardId = 3
WHERE id = 7;

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    memberId = 3,
    boardId = 3;
    
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    memberId = 4,
    boardId = 3;
    
## 게시글 테스트 데이터 대량 생성
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
select now(), now(), floor(RAND() * 2) + 2, FLOOR(RAND() * 3) + 1, CONCAT('제목__', RAND()), CONCAT('내용__', RAND())
from article;


select floor(RAND() * 2) + 2;

SELECT FLOOR(RAND() * 3) + 1;

SELECT *
FROM article;

-- 회원 테스트데이터.
# 관리자.
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'admin',
    loginPw = 'admin',
    `authLevel` = 7,
    `name` = '관리자',
    nickname = '관리자',
    cellphoneNum = '01012341234',
    email = 'abcddd@gmail.com';


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

SELECT a.*, m.nickname AS extra__writer
FROM article a
INNER JOIN `member` m ON a.memberId = m.id
WHERE a.boardId = 1;

