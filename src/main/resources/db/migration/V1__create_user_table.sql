CREATE TABLE "user_entity"
(
    id       bigserial primary key,
    login    varchar(255) not null,
    password varchar(255) not null,
    role_id  bigserial    not null
);