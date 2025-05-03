CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO "user_entity" (login, password, role_id)
VALUES ('Vika', crypt('password123', gen_salt('bf')), 1),
       ('Nikita', crypt('password123', gen_salt('bf')), 2);