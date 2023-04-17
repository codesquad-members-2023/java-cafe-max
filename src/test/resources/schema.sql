
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
                        memberId BIGINT AUTO_INCREMENT,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        nickName VARCHAR(100),
                        create_date TIMESTAMP NOT NULL,
                        PRIMARY KEY (memberId)
);

CREATE TABLE post (
                      postId BIGINT NOT NULL AUTO_INCREMENT,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      writerEmail  VARCHAR(100)  NOT NULL,
                      write_date TIMESTAMP NOT NULL,
                      views BIGINT DEFAULT 0,
                      PRIMARY KEY (postId),
                      FOREIGN KEY (writerEmail) REFERENCES member (email) ON DELETE CASCADE ON UPDATE CASCADE
);