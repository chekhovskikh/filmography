package music;

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
import java.util.Observer;

public interface Model {

    void close() throws SQLException;

    List<Album> getAlbums() throws SQLException, ParseException;

    Album getAlbum(int id) throws SQLException, ParseException;

    List<Band> getBands() throws ParseException, SQLException;

    Band getBand(int id) throws SQLException, ParseException;

    List<Genre> getGenres() throws SQLException, ParseException;

    Genre getGenre(int id) throws SQLException, ParseException;

    List<Song> getSongs() throws SQLException, ParseException;

    Song getSong(int id) throws SQLException, ParseException;

    void addAlbum(Album album) throws SQLException;

    void addAlbums(List<Album> albums) throws SQLException;

    void addBand(Band band) throws SQLException;

    void addBands(List<Band> bands) throws SQLException;

    void addGenre(Genre genre) throws SQLException;

    void addGenres(List<Genre> genres) throws SQLException;

    void addSong(Song song) throws SQLException;

    void addSongs(List<Song> songs) throws SQLException;

    void removeAlbum(int id) throws SQLException;

    void removeBand(int id) throws SQLException;

    void removeGenre(int id) throws SQLException;

    void removeSong(int id) throws SQLException;

    //void save(String fileLocation) throws IOException;

    //void open(String fileLocation) throws IOException;

    void updateAlbum(Album updatedAlbum) throws SQLException;

    void updateBand(Band updatedBand) throws SQLException;

    void updateGenre(Genre updatedGenre) throws SQLException;

    void updateSong(Song updatedSong) throws SQLException;

    List<Band> getBandByName(String name) throws SQLException, ParseException;

    List<Album> getAlbumByName(String name) throws SQLException, ParseException;

    List<Genre> getGenreByName(String name) throws SQLException, ParseException;

    List<Song> getSongByName(String name) throws SQLException, ParseException;

    List<Band> getBandByFilter(BandFilter bandFilter) throws SQLException, ParseException;

    List<Album> getAlbumByFilter(AlbumFilter albumFilter) throws SQLException, ParseException;

    List<Genre> getGenreByFilter(GenreFilter genreFilter) throws SQLException, ParseException;

    List<Song> getSongByFilter(SongFilter songFilter) throws SQLException, ParseException;

    void addObserver(Observer o);

    void deleteObserver(Observer o);
}
