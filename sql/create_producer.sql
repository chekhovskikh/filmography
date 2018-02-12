CREATE TABLE PRODUCER (
producer_id SERIAL Primary Key,
producer_name VARCHAR(255) not null,
birthdate Date not null,
citizenship_id int References COUNTRY(country_id) on delete restrict not null);