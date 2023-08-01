-- liquibase formatted sql
--changeset velikorodnova:init_tables

create table users(
        id      serial not null primary key,
        email varchar(50) unique not null,
        first_name varchar(50) not null,
        image varchar(100),
        last_name varchar(50) not null,
        password varchar(255) not null,
        phone varchar(15) not null,
        role varchar(5) not null
);