package mvc.dao;

import dao.EntityDao;
import entities.Album;
import entities.Entity;
import entities.filters.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AlbumStubDao implements EntityDao<Album> {
    private List<Album> albums;

    public AlbumStubDao() {
        albums = new ArrayList<>();
    }

    public AlbumStubDao(List<Album> albums) {
        this.albums = albums;
    }


    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Album> getAll() throws SQLException, ParseException {
        return albums;
    }

    public Album get(int id) throws SQLException, ParseException {
        return albums.get(id);
    }

    public List<Album> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Album album) throws SQLException {
        albums.add(album);
    }

    public void addAll(List<Album> entities) throws SQLException {
        albums.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        albums.remove(id);
    }

    public void update(Album entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Album> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
