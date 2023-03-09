CREATE TYPE task_status AS ENUM(
    'SCHEDULED',
    'PROCESSING',
    'SUCCEEDED',
    'FAILED'
);

CREATE TABLE task(
    id UUID NOT NULL,
    partition INT NOT NULL,
    version BIGINT NOT NULL,
    name VARCHAR NOT NULL,
    status task_status NOT NULL,
    data TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    execute_at TIMESTAMP NOT NULL,
    processing_started_at TIMESTAMP,
    processing_retries_count INT,

    PRIMARY KEY (id),
    UNIQUE (id, partition)
);
