CREATE TABLE GENRE (
genre_id SERIAL Primary Key,
genre_name VARCHAR(255) not null UNIQUE,
parent_id int References GENRE(genre_id) on delete set null);