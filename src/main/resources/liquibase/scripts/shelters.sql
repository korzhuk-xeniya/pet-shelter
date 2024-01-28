------liquibase formatted sql
------
------changeset kkorzhuk:1
CREATE TABLE pet_shelters (
id UUID,
nameOfShelter TEXT,
aboutShelter TEXT,
address TEXT,
phoneNumber varchar(50),
CONSTRAINT pet_shelters_pk PRIMARY KEY (id)
);