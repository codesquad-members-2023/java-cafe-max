CREATE TABLE IF NOT EXISTS USER_INFO
(
    index      bigint       AUTO_INCREMENT,
    userID     VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    nickname   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    signUpDate DATE         NOT NULL,
    PRIMARY KEY (index),
    UNIQUE (nickname)
);

CREATE TABLE IF NOT EXISTS WRITE_INFO
(
    index     bigint       AUTO_INCREMENT,
    title     VARCHAR(255) NOT NULL,
    writer    VARCHAR(255) NOT NULL,
    contents  TEXT         NOT NULL,
    writeDate TIMESTAMP(0) NOT NULL,
    hits      bigint       NOT NULL,
    deleted   BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (index),
    FOREIGN KEY (writer)
    REFERENCES USER_INFO(nickname) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS COMMENT_INFO
(
    index       bigint       AUTO_INCREMENT,
    postIndex   bigint       NOT NULL,
    author      VARCHAR(255) NOT NULL,
    comment     TEXT         NOT NULL,
    createdDate TIMESTAMP(0) NOT NULL,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (index),
    FOREIGN KEY (author)
    REFERENCES USER_INFO(nickname) ON UPDATE CASCADE
);
