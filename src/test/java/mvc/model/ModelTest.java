package mvc.model;

import com.google.inject.Injector;
import entities.Album;
import entities.Band;
import entities.Genre;
import entities.Song;
import music.Model;
import mvc.guice.InjectorStubBuilder;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelTest {
    protected List<Genre> genres;
    protected List<Band> bands;
    protected List<Album> albums;
    protected List<Song> songs;
    protected Model model;

    @Before
    public void init() {
        genres = new ArrayList<>();
        bands = new ArrayList<>();
        albums = new ArrayList<>();
        songs = new ArrayList<>();

        Injector injector = new InjectorStubBuilder(genres, bands, albums, songs).getInjector();
        model = injector.getInstance(Model.class);
    }
}
