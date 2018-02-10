package mvc.dao;

import dao.EntityDao;
import entitiy.Film;
import entitiy.filter.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FilmStubDao implements EntityDao<Film> {
    private List<Film> films;

    public FilmStubDao() {
        films = new ArrayList<>();
    }

    public FilmStubDao(List<Film> films) {
        this.films = films;
    }


    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Film> getAll() throws SQLException, ParseException {
        return films;
    }

    public Film get(int id) throws SQLException, ParseException {
        return films.get(id);
    }

    public List<Film> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Film film) throws SQLException {
        films.add(film);
    }

    public void addAll(List<Film> entities) throws SQLException {
        films.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        films.remove(id);
    }

    public void update(Film entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Film> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
