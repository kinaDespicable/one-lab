CREATE TABLE IF NOT EXISTS users(
    id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(64) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    CONSTRAINT pk__users__user_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles(
    id BIGINT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk__roles__role_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles(
    user_id BIGINT,
    role_id BIGINT,
    CONSTRAINT fk__users_roles__user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk__users_roles__role_id FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS  topics(
    id BIGINT AUTO_INCREMENT NOT NULL,
    topic_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk__topic__topic_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS news(
    id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(1000),
    likes BIGINT NOT NULL DEFAULT 0,
    published_at DATETIME,
    author_id BIGINT,
    topic_id BIGINT,
    CONSTRAINT pk__news__news_id PRIMARY KEY (id),
    CONSTRAINT fk__news__author_id__user FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT fk__news__topic_id__topic FOREIGN KEY (topic_id) REFERENCES topics (id)
);