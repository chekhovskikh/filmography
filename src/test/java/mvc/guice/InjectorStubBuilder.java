package mvc.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import entities.Album;
import entities.Band;
import entities.Genre;
import entities.Song;
import music.guice.ControllerModule;
import music.guice.ModelModule;

import java.util.List;

public class InjectorStubBuilder {
    protected List<Genre> genres;
    protected List<Band> bands;
    protected List<Album> albums;
    protected List<Song> songs;

    public InjectorStubBuilder(List<Genre> genres, List<Band> bands, List<Album> albums, List<Song> songs) {
        this.genres = genres;
        this.bands = bands;
        this.albums = albums;
        this.songs = songs;
    }
    public Injector getInjector() {
        return Guice.createInjector(
                new DaoStubModule(genres, bands, albums, songs),
                new ModelModule(),
                new ControllerModule());
    }
}
