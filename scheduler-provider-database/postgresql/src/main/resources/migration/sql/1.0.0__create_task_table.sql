CREATE TABLE task(
    partition INT NOT NULL,
    id UUID NOT NULL,
    version BIGINT NOT NULL,
    status VARCHAR NOT NULL,
    execute_at TIMESTAMP NOT NULL,
    name VARCHAR NOT NULL,
    data TEXT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (id, partition)
);

CREATE INDEX ON task(status, execute_at);
