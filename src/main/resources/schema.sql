DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS article CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY ,
    username    VARCHAR (40) NOT NULL COMMENT '로그인ID',
    password    VARCHAR (255) NOT NULL,
    nickname    VARCHAR (20) NOT NULL,
    email       VARCHAR (40) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS article
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY,
    title       VARCHAR (30) NOT NULL,
    user_id     BIGINT NOT NULL,
    writer      VARCHAR (20) NOT NULL,
    contents    TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3),

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
)