CREATE TABLE USERS
(
    IDX      INT AUTO_INCREMENT,
    ID       VARCHAR(255),
    PASSWORD VARCHAR(255) NOT NULL,
    NAME     VARCHAR(255) NOT NULL,
    EMAIL    VARCHAR(255) NOT NULL,
    PRIMARY KEY (IDX),
    UNIQUE (NAME)
);


CREATE TABLE ARTICLES
(
    IDX      INT AUTO_INCREMENT,
    ID       VARCHAR(255),
    WRITER   VARCHAR(255)  NOT NULL,
    TITLE    VARCHAR(255)  NOT NULL,
    CONTENTS VARCHAR(1000) NOT NULL,
    DATE     TIMESTAMP              DEFAULT CURRENT_TIMESTAMP(0) NOT NULL,
    DELETED  BOOLEAN       NOT NULL DEFAULT FALSE,
    PRIMARY KEY (IDX),
    FOREIGN KEY (WRITER)
        REFERENCES USERS (NAME) ON UPDATE CASCADE
);

CREATE TABLE REPLY
(
    IDX            INT AUTO_INCREMENT,
    ARTICLE_IDX    INT,
    REPLY_WRITER   VARCHAR(255)  NOT NULL,
    REPLY_CONTENTS VARCHAR(1000) NOT NULL,
    DATE           TIMESTAMP              DEFAULT CURRENT_TIMESTAMP(0) NOT NULL,
    DELETED        BOOLEAN       NOT NULL DEFAULT FALSE,
    PRIMARY KEY (IDX),
    FOREIGN KEY (REPLY_WRITER)
        REFERENCES USERS (NAME) ON UPDATE CASCADE
);