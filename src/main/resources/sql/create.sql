DROP TABLE IF EXISTS people CASCADE;
CREATE TABLE people (
    person_id           serial          PRIMARY KEY,
    name                text           NOT NULL,
    phone_num           text,
    email               text,
    education           text,
    experience          int,
    status              bool        NOT NULL,
    wanted_position     text,
    wanted_salary       int
);

DROP TABLE IF EXISTS companies CASCADE;
CREATE TABLE companies (
                           company_id          serial      PRIMARY KEY,
                           name                text        NOT NULL,
                           specialization      text        NOT NULL,
                           phone_num           text,
                           email               text
);

DROP TABLE IF EXISTS working_places CASCADE;
CREATE TABLE working_places (
    place_id serial PRIMARY KEY,
    person_id int NOT NULL,
    company_name text NOT NULL,
    startofperiod date NOT NULL,
    endofperiod date NOT NULL,
    position text NOT NULL,
    foreign key (person_id) references people(person_id) on delete cascade
);

DROP TABLE IF EXISTS vacancies CASCADE;
CREATE TABLE vacancies (
    vacancy_id      serial PRIMARY KEY,
    company_id      int NOT NULL,
    position        text   NOT NULL,
    salary          int   NOT NULL,
    education       text,
    experience      int,
    other           text,
    foreign key (company_id) REFERENCES companies(company_id) on delete cascade
);
