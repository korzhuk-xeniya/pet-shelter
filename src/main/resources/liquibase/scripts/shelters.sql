------liquibase formatted sql
------
------changeset kkorzhuk:1
CREATE TABLE pet_shelters (
id UUID generated by default as identity primary key,
name_of_shelter VARCHAR,
about_shelter VARCHAR,
address VARCHAR,
phone_number VARCHAR(50)
--CONSTRAINT pet_shelters_pk PRIMARY KEY (id)
);
CREATE TABLE user_tg
(
    id                UUID generated by default as identity primary key,
    first_name        VARCHAR not null,
    took_a_pet        BOOLEAN,
    date_time_to_took TIMESTAMP,
    chat_id           INT not null,
    telephone_number  VARCHAR(50)
);

--CREATE TABLE report_tg
--(
--    id                 BIGINT generated by default as identity primary key,
--    date_added         TIMESTAMP,
--    general_well_being TEXT,
--    photo_name         TEXT,
--    user_id            BIGINT references user_tg (id) on delete set null,
--    check_report       bool
--);

CREATE TABLE volunteers
(
    id_volunteer      BIGINT generated by default as identity primary key,
   name_volunteer     TEXT   not null,
   last_name_volunteer     TEXT   not null,
    chat_id_volunteer BIGINT   not null

);