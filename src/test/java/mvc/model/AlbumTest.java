package mvc.model;

import entities.Album;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class AlbumTest extends ModelTest {

    @Test
    public void testAddAlbum() throws SQLException, IOException {
        Album album = new Album("Californication", 1, new Date());
        Album album1 = new Album("Stadium Arcadium", 1, new Date());
        model.addAlbum(album);
        model.addAlbum(album1);
        assertEquals(album, albums.get(0));
    }

    @Test
    public void testRemoveAlbum() throws SQLException, IOException {
        Album album = new Album("Californication", 1, new Date());
        Album album1 = new Album("Stadium Arcadium", 1, new Date());
        model.addAlbum(album);
        assertEquals(album, albums.get(0));
        model.addAlbum(album1);
        assertEquals(album1, albums.get(1));
        model.removeAlbum(0);
        assertEquals(1, albums.size());
    }

    @Test
    public void testGetAlbum() throws SQLException, IOException, ParseException {
        Album album = new Album("Californication", 1, new Date());
        Album album1 = new Album("Stadium Arcadium", 1, new Date());
        model.addAlbum(album);
        model.addAlbum(album1);
        Album album2 = model.getAlbum(1);
        assertEquals(album2, album1);
    }
}
