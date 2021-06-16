CREATE TABLE project
(
    id           INTEGER      NOT NULL AUTO_INCREMENT,
    name         VARCHAR(128) NOT NULL,
    date_created DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE task
(
    id          INTEGER      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(128) NOT NULL,
    date_created DATE         NOT NULL,
    due_date     DATE         NOT NULL,
    status      VARCHAR(32) NOT NULL,
    project_id  INTEGER,
    PRIMARY KEY (id)
);