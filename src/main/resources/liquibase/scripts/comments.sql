-- liquibase formatted sql
--changeset velikorodnova:init_tables

create table comments(
        id      integer not null primary key,
        dates timestamp,
        texts varchar(255) not null,
        ad integer not null references ad (id),
        author integer not null references users (id)
);