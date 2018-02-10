package music;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.EntityDao;
import entities.Album;
import entities.Band;
import entities.Genre;
import entities.Song;
import entities.filters.AlbumFilter;
import entities.filters.BandFilter;
import entities.filters.GenreFilter;
import entities.filters.SongFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Observable;

public class MusicModel extends Observable implements Model {

    private EntityDao<Song> songDAO;
    private EntityDao<Album> albumDAO;
    private EntityDao<Band> bandDAO;
    private EntityDao<Genre> genreDAO;

    @Inject
    public MusicModel(@Named("song")EntityDao songDAO,
                      @Named("album")EntityDao albumDAO,
                      @Named("band")EntityDao bandDAO,
                      @Named("genre")EntityDao genreDAO) {

        if (songDAO == null || albumDAO == null || bandDAO == null || genreDAO == null)
            throw new NullPointerException("EntityDao is null");
        this.songDAO = songDAO;
        this.albumDAO = albumDAO;
        this.bandDAO = bandDAO;
        this.genreDAO = genreDAO;
    }

    public void close() throws SQLException {
        songDAO.closeConnection();
        albumDAO.closeConnection();
        bandDAO.closeConnection();
        genreDAO.closeConnection();
    }

    private void notifyViews(EventType e) {
        setChanged();
        notifyObservers(e);
    }

    //region GET
    public List<Album> getAlbums() throws SQLException, ParseException {
        return albumDAO.getAll();
    }

    public Album getAlbum(int id) throws SQLException, ParseException {
        return albumDAO.get(id);
    }

    public List<Band> getBands() throws SQLException, ParseException {
        return bandDAO.getAll();
    }

    public Band getBand(int id) throws SQLException, ParseException {
        return bandDAO.get(id);
    }

    public List<Genre> getGenres() throws SQLException, ParseException {
        return genreDAO.getAll();
    }

    public Genre getGenre(int id) throws SQLException, ParseException {
        return genreDAO.get(id);
    }

    public List<Song> getSongs() throws SQLException, ParseException {
        return songDAO.getAll();
    }

    public Song getSong(int id) throws SQLException, ParseException {
        return songDAO.get(id);
    }
    //endregion GET

    //region ADD
    public void addAlbum(Album album) throws SQLException {
        albumDAO.add(album);
        notifyViews(EventType.ALBUMS_CHANGE);
    }

    public void addAlbums(List<Album> albums) throws SQLException {
        albumDAO.addAll(albums);
        notifyViews(EventType.ALBUMS_CHANGE);
    }

    public void addBand(Band band) throws SQLException {
        bandDAO.add(band);
        notifyViews(EventType.BANDS_CHANGE);
    }

    public void addBands(List<Band> bands) throws SQLException {
        bandDAO.addAll(bands);
        notifyViews(EventType.BANDS_CHANGE);
    }

    public void addGenre(Genre genre) throws SQLException {
        genreDAO.add(genre);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void addGenres(List<Genre> genres) throws SQLException {
        genreDAO.addAll(genres);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void addSong(Song song) throws SQLException {
        songDAO.add(song);
        notifyViews(EventType.SONGS_CHANGE);
    }

    public void addSongs(List<Song> songs) throws SQLException {
        songDAO.addAll(songs);
        notifyViews(EventType.SONGS_CHANGE);
    }
    //endregion ADD

    //region REMOVE

    public void removeAlbum(int id) throws SQLException {
        albumDAO.remove(id);
        notifyViews(EventType.ALBUMS_CHANGE);
    }

    public void removeBand(int id) throws SQLException {
        bandDAO.remove(id);
        notifyViews(EventType.BANDS_CHANGE);
    }

    public void removeGenre(int id) throws SQLException {
        genreDAO.remove(id);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void removeSong(int id) throws SQLException {
        songDAO.remove(id);
        notifyViews(EventType.SONGS_CHANGE);
    }
    //endregion REMOVE

    //region UPDATE
    public void updateAlbum(Album updatedAlbum) throws SQLException {
        albumDAO.update(updatedAlbum);
        notifyViews(EventType.ALBUMS_CHANGE);
    }

    public void updateBand(Band updatedBand) throws SQLException {
        bandDAO.update(updatedBand);
        notifyViews(EventType.BANDS_CHANGE);
    }

    public void updateGenre(Genre updatedGenre) throws SQLException {
        genreDAO.update(updatedGenre);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void updateSong(Song updatedSong) throws SQLException {
        songDAO.update(updatedSong);
        notifyViews(EventType.SONGS_CHANGE);
    }
    //endregion UPDATE

    //region SEARCH
    public List<Band> getBandByName(String name) throws SQLException, ParseException {
        return bandDAO.getByName(name);
    }

    public List<Album> getAlbumByName(String name) throws SQLException, ParseException {
        return albumDAO.getByName(name);
    }

    public List<Genre> getGenreByName(String name) throws SQLException, ParseException {
        return genreDAO.getByName(name);
    }

    public List<Song> getSongByName(String name) throws SQLException, ParseException {
        return songDAO.getByName(name);
    }
    //endregion SEARCH

    //region FILTER
    @Override
    public List<Band> getBandByFilter(BandFilter bandFilter) throws SQLException, ParseException {
        return bandDAO.getByFilter(bandFilter);
    }

    @Override
    public List<Album> getAlbumByFilter(AlbumFilter albumFilter) throws SQLException, ParseException {
        return albumDAO.getByFilter(albumFilter);
    }

    @Override
    public List<Genre> getGenreByFilter(GenreFilter genreFilter) throws SQLException, ParseException {
        return genreDAO.getByFilter(genreFilter);
    }

    @Override
    public List<Song> getSongByFilter(SongFilter songFilter) throws SQLException, ParseException {
        return songDAO.getByFilter(songFilter);
    }
    //endregion FILTER
}
