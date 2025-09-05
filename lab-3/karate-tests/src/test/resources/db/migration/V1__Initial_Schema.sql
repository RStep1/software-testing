CREATE TABLE plan (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255)
);

CREATE TABLE event (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    time_start BIGINT,
    time_end BIGINT,
    plan_id BIGINT,
    FOREIGN KEY plan_id REFERENCES plan(id) ON DELETE CASCADE
);