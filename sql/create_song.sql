CREATE SEQUENCE song_seq;

CREATE TABLE SONG (
ID int Primary Key,
NAME VarChar2(50) not null,
TIME Date not null,
GENRE_ID int References GENRE(ID) on delete cascade,
BAND_ID int References BAND(ID) on delete cascade not null,
ALBUM_ID int References ALBUM(ID) on delete cascade);

CREATE OR REPLACE TRIGGER song_trigger
BEFORE INSERT ON SONG FOR EACH ROW
BEGIN
:NEW.ID := song_seq.NEXTVAL;
END;