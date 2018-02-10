CREATE SEQUENCE album_seq;

CREATE TABLE ALBUM (
ID int Primary Key,
NAME VarChar2(20) not null,
RELEASE Date not null,
BAND_ID int References BAND(ID) on delete cascade not null);

CREATE OR REPLACE TRIGGER album_trigger
BEFORE INSERT ON ALBUM FOR EACH ROW
BEGIN
:NEW.ID := album_seq.NEXTVAL;
END;