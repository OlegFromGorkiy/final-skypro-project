-- liquibase formatted sql
--changeset velikorodnova:init_tables

create table ad(
        id      serial primary key,
        description varchar(255) not null,
        image varchar(255),
        price integer not null,
        title varchar(255) not null,
        author integer not null references users (id)
);