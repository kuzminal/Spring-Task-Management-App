CREATE TABLE IF NOT EXISTS project
(
    id           SERIAL       NOT NULL,
    name         VARCHAR(128) NOT NULL,
    date_created DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS task
(
    id           SERIAL       NOT NULL,
    name         VARCHAR(128) NOT NULL,
    description  VARCHAR(128),
    date_created DATE         NOT NULL,
    due_date     DATE         NOT NULL,
    status       VARCHAR(32)  NOT NULL,
    project_id   INTEGER,
    PRIMARY KEY (id)
);