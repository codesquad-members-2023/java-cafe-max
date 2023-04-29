DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS "post" CASCADE;

CREATE TABLE IF NOT EXISTS "user" (
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId    VARCHAR(20) NOT NULL UNIQUE,
    password  VARCHAR(32) NOT NULL,
    name      VARCHAR(49) NOT NULL,
    email     VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS "post" (
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    writer_id        BIGINT NOT NULL,
    title     VARCHAR(50) NOT NULL,
    contents  TEXT NOT NULL,
    is_deleted  BOOLEAN NOT NULL DEFAULT FALSE,
    registrationDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "comment" (
    id  BIGINT  PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT  NOT NULL,
    writer_id   BIGINT  NOT NULL,
    content TEXT NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    registrationDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)
