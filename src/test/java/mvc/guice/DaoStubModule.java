package mvc.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import dao.*;
import entities.Album;
import entities.Band;
import entities.Genre;
import entities.Song;
import mvc.dao.*;

import java.util.List;

public class DaoStubModule implements Module {
    protected List<Genre> genres;
    protected List<Band> bands;
    protected List<Album> albums;
    protected List<Song> songs;

    public DaoStubModule(List<Genre> genres, List<Band> bands, List<Album> albums, List<Song> songs) {
        this.genres = genres;
        this.bands = bands;
        this.albums = albums;
        this.songs = songs;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(EntityDao.class).annotatedWith(Names.named("genre")).toInstance(new GenreStubDao(genres));
        binder.bind(EntityDao.class).annotatedWith(Names.named("band")).toInstance(new BandStubDao(bands));
        binder.bind(EntityDao.class).annotatedWith(Names.named("album")).toInstance(new AlbumStubDao(albums));
        binder.bind(EntityDao.class).annotatedWith(Names.named("song")).toInstance(new SongStubDao(songs));
    }
}
