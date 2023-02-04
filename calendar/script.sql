create table account
(
    id      serial
            primary key,
    username    varchar(255) unique not null
);

create table event
(
    id      serial
        primary key,
    name    varchar(255) unique not null,
    date    date,
    time    time,
    description varchar(512),
    event_creator integer
        constraint event_account_fk
            references account,
    importance_of_event varchar(255)
);

alter table event rename column event_creator to account;