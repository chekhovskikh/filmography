package music;

import entities.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface Controller {

    Model getModel();

    void addGenre(Genre genre) throws SQLException;

    void addBand(Band band) throws SQLException;

    void addAlbum(Album album) throws SQLException;

    void addSong(Song song) throws SQLException;

    void addGenres(List<Genre> genres) throws SQLException;

    void addBands(List<Band> bands) throws SQLException;

    void addAlbums(List<Album> albums) throws SQLException;

    void addSongs(List<Song> songs) throws SQLException;

    void removeGenre(int id) throws SQLException;

    void removeBand(int id) throws SQLException;

    void removeAlbum(int id) throws SQLException;

    void removeSong(int id) throws SQLException;

    void updateAlbum(Album updatedAlbum) throws SQLException;

    void updateBand(Band updatedBand) throws SQLException;

    void updateGenre(Genre updatedGenre) throws SQLException;

    void updateSong(Song updatedSong) throws SQLException;

    //void save(String fileLocation) throws IOException;

    //void open(String fileLocation) throws IOException;
}
