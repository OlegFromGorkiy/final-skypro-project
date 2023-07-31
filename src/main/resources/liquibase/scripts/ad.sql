-- liquibase formatted sql
--changeset velikorodnova:init_tables

create table ad(
        id      integer not null primary key,
        description varchar(255) not null,
        image varchar(255) not null,
        price integer not null,
        title varchar(255) not null,
        author integer not null references users (id)
);