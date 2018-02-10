package mvc.model;

import entitiy.Film;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class FilmTest extends ModelTest {

    @Test
    public void testAddFilm() throws SQLException, IOException {
        Date duration = new Date();
        Film film = new Film("California", duration, 3, 4, 3);
        Film film1 = new Film("It", duration, 2, 1, 3);
        model.addFilm(film);
        model.addFilm(film1);
        assertEquals(film, films.get(0));
        assertEquals(film1, films.get(1));
    }

    @Test
    public void testRemoveFilm() throws SQLException, IOException {
        Date duration = new Date();
        Film film = new Film("California", duration, 3, 4, 3);
        Film film1 = new Film("It", duration, 2, 1, 3);
        model.addFilm(film);
        assertEquals(film, films.get(0));
        model.addFilm(film1);
        assertEquals(film1, films.get(1));
        model.removeFilm(0);
        assertEquals(1, films.size());
    }

    @Test
    public void testGetFilm() throws SQLException, IOException, ParseException {
        Date duration = new Date();
        Film film = new Film("California", duration, 3, 4, 3);
        Film film1 = new Film("It", duration, 2, 1, 3);
        model.addFilm(film);
        model.addFilm(film1);
        Film film2 = model.getFilm(1);
        assertEquals(film1, film2);
    }
}
