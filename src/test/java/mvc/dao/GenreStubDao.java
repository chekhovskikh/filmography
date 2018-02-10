package mvc.dao;

import dao.EntityDao;
import entities.Genre;
import entities.filters.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GenreStubDao implements EntityDao<Genre> {

    private List<Genre> genres;

    public GenreStubDao() {
        genres = new ArrayList<>();
    }

    public GenreStubDao(List<Genre> genres) {
        this.genres = genres;
    }

    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Genre> getAll() throws SQLException, ParseException {
        return genres;
    }

    public Genre get(int id) throws SQLException, ParseException {
        return genres.get(id);
    }

    public List<Genre> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Genre genre) throws SQLException {
        genres.add(genre);
    }

    public void addAll(List<Genre> entities) throws SQLException {
        genres.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        genres.remove(id);
    }

    public void update(Genre entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Genre> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
