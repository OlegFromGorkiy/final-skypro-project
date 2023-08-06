-- liquibase formatted sql
--changeset velikorodnova:init_tables

create table comments
(
    id     serial not null primary key,
    "date" timestamp,
    "text" varchar(255),
    ad     integer not null references ad (id),
    author integer not null references users (id)
);