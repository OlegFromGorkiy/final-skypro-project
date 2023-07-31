-- liquibase formatted sql
--changeset velikorodnova:init_tables

create table users(
        id      integer not null primary key,
        email varchar(255) unique not null,
        first_name varchar(255) not null,
        image varchar(255),
        last_name varchar(255) not null,
        password varchar(255) not null,
        phone varchar(255) not null,
        role varchar(255) not null
);