package mvc.dao;

import dao.EntityDao;
import entities.Song;
import entities.filters.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SongStubDao implements EntityDao<Song> {
    private List<Song> songs;

    public SongStubDao() {
        songs = new ArrayList<>();
    }

    public SongStubDao(List<Song> songs) {
        this.songs = songs;
    }


    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Song> getAll() throws SQLException, ParseException {
        return songs;
    }

    public Song get(int id) throws SQLException, ParseException {
        return songs.get(id);
    }

    public List<Song> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Song song) throws SQLException {
        songs.add(song);
    }

    public void addAll(List<Song> entities) throws SQLException {
        songs.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        songs.remove(id);
    }

    public void update(Song entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Song> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
