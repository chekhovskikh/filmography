package mvc.model;

import entities.Song;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class SongTest extends ModelTest {

    @Test
    public void testAddSong() throws SQLException, IOException {
        Song song = new Song("Californication", new Date(), 3, 4, 3);
        Song song1 = new Song("Bla-bla", new Date(), 2, 1, 3);
        model.addSong(song);
        model.addSong(song1);
        assertEquals(song, songs.get(0));
        assertEquals(song1, songs.get(1));
    }

    @Test
    public void testRemoveSong() throws SQLException, IOException {
        Song song = new Song("Californication", new Date(), 3, 4, 3);
        Song song1 = new Song("Bla-bla", new Date(), 2, 1, 3);
        model.addSong(song);
        assertEquals(song, songs.get(0));
        model.addSong(song1);
        assertEquals(song1, songs.get(1));
        model.removeSong(0);
        assertEquals(1, songs.size());
    }

    @Test
    public void testGetGenre() throws SQLException, IOException, ParseException {
        Song song = new Song("Californication", new Date(), 3, 4, 3);
        Song song1 = new Song("Bla-bla", new Date(), 2, 1, 3);
        model.addSong(song);
        model.addSong(song1);
        Song song2 = model.getSong(1);
        assertEquals(song1, song2);
    }
}
