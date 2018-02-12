CREATE TABLE FRANCHISE (
franchise_id SERIAL Primary Key,
franchise_name VARCHAR(255) not null,
release Date not null,
country_id int References COUNTRY(country_id) on delete restrict not null);