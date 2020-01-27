CREATE SEQUENCE hibernate_sequence;

CREATE TABLE account (
     username   VARCHAR PRIMARY KEY,
     password   VARCHAR NOT NULL,
     name       VARCHAR NOT NULL,
     surname    VARCHAR NOT NULL,
     birth      VARCHAR NOT NULL
);

CREATE TABLE post (
    id          serial  PRIMARY KEY,
    title       VARCHAR NOT NULL,
    content     VARCHAR NOT NULL,
    added_at    VARCHAR NOT NULL,
    author      VARCHAR NOT NULL,

    FOREIGN KEY (author) REFERENCES account(username)
);