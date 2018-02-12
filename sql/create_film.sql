CREATE TABLE FILM (
film_id SERIAL Primary Key,
film_name VARCHAR(255) not null,
duration Time not null,
producer_id int References PRODUCER(producer_id) on delete cascade not null,
genre_id int References GENRE(genre_id) on delete set null,
franchise_id int References FRANCHISE(franchise_id) on delete set null);