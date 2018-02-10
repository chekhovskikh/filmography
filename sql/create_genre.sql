CREATE SEQUENCE genre_seq;

CREATE TABLE GENRE (
ID int Primary Key,
NAME VarChar2(20) not null UNIQUE,
PARENT_ID int References GENRE(ID) on delete cascade);

CREATE OR REPLACE TRIGGER genre_trigger
BEFORE INSERT ON GENRE FOR EACH ROW
BEGIN
:NEW.ID := genre_seq.NEXTVAL;
END;