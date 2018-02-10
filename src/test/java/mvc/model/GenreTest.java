package mvc.model;

import entitiy.Genre;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public  class GenreTest extends ModelTest {

    @Test
    public void testAddGenre() throws SQLException, IOException, ParseException {
        Genre genre = new Genre(1, "Rock");
        Genre genre1 = new Genre("Rap", 1);
        model.addGenre(genre);
        model.addGenre(genre1);
        assertEquals(genre, genres.get(0));
        //model.close();
    }

    @Test
    public void testRemoveGenre() throws SQLException, IOException {
        Genre genre = new Genre("Rap");
        model.addGenre(genre);
        assertEquals(genre, genres.get(0));
        model.removeGenre(0);
        assertEquals(0, genres.size());
    }

    @Test
    public void testGetGenre() throws SQLException, IOException, ParseException {
        Genre genre = new Genre("Rap");
        Genre genre1 = new Genre("Pop", 1);
        model.addGenre(genre);
        model.addGenre(genre1);
        Genre genre2 = model.getGenre(1);
        assertEquals(genre2, genre1);
    }

}
