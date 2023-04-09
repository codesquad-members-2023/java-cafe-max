CREATE TABLE IF NOT EXISTS Users (
  userId    VARCHAR(50) PRIMARY KEY,
  password  VARCHAR(50) NOT NULL,
  name      VARCHAR(50) NOT NULL,
  email     VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Articles (
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  title     VARCHAR(50) NOT NULL,
  writer    VARCHAR(50) NOT NULL,
  contents  TEXT NOT NULL,
  createdAt TIMESTAMP NOT NULL
);

