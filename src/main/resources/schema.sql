CREATE TABLE IF NOT EXISTS users(
    user_id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    admin BOOLEAN NOT NULL DEFAULT false,
    author BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT pk__users__user_id PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS  topics(
    topic_id BIGINT AUTO_INCREMENT NOT NULL,
    topic_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk__topic__topic_id PRIMARY KEY (topic_id)
);

CREATE TABLE IF NOT EXISTS news(
    news_id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    likes BIGINT NOT NULL DEFAULT 0,
    published_at DATETIME,
    author_id BIGINT,
    topic_id BIGINT,
    CONSTRAINT fk__news__author_id__user FOREIGN KEY (author_id) REFERENCES users (user_id),
    CONSTRAINT fk__news__topic_id__topic FOREIGN KEY (topic_id) REFERENCES topics (topic_id)
);