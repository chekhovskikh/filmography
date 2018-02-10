package mvc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.EntityDao;
import entitiy.Film;
import entitiy.Franchise;
import entitiy.Producer;
import entitiy.Genre;
import entitiy.filter.FranchiseFilter;
import entitiy.filter.ProducerFilter;
import entitiy.filter.GenreFilter;
import entitiy.filter.FilmFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Observable;

public class FilmModel extends Observable implements Model {

    private EntityDao<Film> filmDAO;
    private EntityDao<Franchise> franchiseDAO;
    private EntityDao<Producer> producerDAO;
    private EntityDao<Genre> genreDAO;

    @Inject
    public FilmModel(@Named("film")EntityDao filmDAO,
                     @Named("franchise")EntityDao franchiseDAO,
                     @Named("producer")EntityDao producerDAO,
                     @Named("genre")EntityDao genreDAO) {

        if (filmDAO == null || franchiseDAO == null || producerDAO == null || genreDAO == null)
            throw new NullPointerException("EntityDao is null");
        this.filmDAO = filmDAO;
        this.franchiseDAO = franchiseDAO;
        this.producerDAO = producerDAO;
        this.genreDAO = genreDAO;
    }

    public void close() throws SQLException {
        filmDAO.closeConnection();
        franchiseDAO.closeConnection();
        producerDAO.closeConnection();
        genreDAO.closeConnection();
    }

    private void notifyViews(EventType e) {
        setChanged();
        notifyObservers(e);
    }

    //region GET
    public List<Franchise> getFranchises() throws SQLException, ParseException {
        return franchiseDAO.getAll();
    }

    public Franchise getFranchise(int id) throws SQLException, ParseException {
        return franchiseDAO.get(id);
    }

    public List<Producer> getProducers() throws SQLException, ParseException {
        return producerDAO.getAll();
    }

    public Producer getProducer(int id) throws SQLException, ParseException {
        return producerDAO.get(id);
    }

    public List<Genre> getGenres() throws SQLException, ParseException {
        return genreDAO.getAll();
    }

    public Genre getGenre(int id) throws SQLException, ParseException {
        return genreDAO.get(id);
    }

    public List<Film> getFilms() throws SQLException, ParseException {
        return filmDAO.getAll();
    }

    public Film getFilm(int id) throws SQLException, ParseException {
        return filmDAO.get(id);
    }
    //endregion GET

    //region ADD
    public void addFranchise(Franchise franchise) throws SQLException {
        franchiseDAO.add(franchise);
        notifyViews(EventType.FRANCHISES_CHANGE);
    }

    public void addFranchises(List<Franchise> franchises) throws SQLException {
        franchiseDAO.addAll(franchises);
        notifyViews(EventType.FRANCHISES_CHANGE);
    }

    public void addProducer(Producer producer) throws SQLException {
        producerDAO.add(producer);
        notifyViews(EventType.PRODUCERS_CHANGE);
    }

    public void addProducers(List<Producer> producers) throws SQLException {
        producerDAO.addAll(producers);
        notifyViews(EventType.PRODUCERS_CHANGE);
    }

    public void addGenre(Genre genre) throws SQLException {
        genreDAO.add(genre);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void addGenres(List<Genre> genres) throws SQLException {
        genreDAO.addAll(genres);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void addFilm(Film film) throws SQLException {
        filmDAO.add(film);
        notifyViews(EventType.FILMS_CHANGE);
    }

    public void addFilms(List<Film> films) throws SQLException {
        filmDAO.addAll(films);
        notifyViews(EventType.FILMS_CHANGE);
    }
    //endregion ADD

    //region REMOVE

    public void removeFranchise(int id) throws SQLException {
        franchiseDAO.remove(id);
        notifyViews(EventType.FRANCHISES_CHANGE);
    }

    public void removeProducer(int id) throws SQLException {
        producerDAO.remove(id);
        notifyViews(EventType.PRODUCERS_CHANGE);
    }

    public void removeGenre(int id) throws SQLException {
        genreDAO.remove(id);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void removeFilm(int id) throws SQLException {
        filmDAO.remove(id);
        notifyViews(EventType.FILMS_CHANGE);
    }
    //endregion REMOVE

    //region UPDATE
    public void updateFranchise(Franchise updatedFranchise) throws SQLException {
        franchiseDAO.update(updatedFranchise);
        notifyViews(EventType.FRANCHISES_CHANGE);
    }

    public void updateProducer(Producer updatedProducer) throws SQLException {
        producerDAO.update(updatedProducer);
        notifyViews(EventType.PRODUCERS_CHANGE);
    }

    public void updateGenre(Genre updatedGenre) throws SQLException {
        genreDAO.update(updatedGenre);
        notifyViews(EventType.GENRES_CHANGE);
    }

    public void updateFilm(Film updatedFilm) throws SQLException {
        filmDAO.update(updatedFilm);
        notifyViews(EventType.FILMS_CHANGE);
    }
    //endregion UPDATE

    //region SEARCH
    public List<Producer> getProducerByName(String name) throws SQLException, ParseException {
        return producerDAO.getByName(name);
    }

    public List<Franchise> getFranchiseByName(String name) throws SQLException, ParseException {
        return franchiseDAO.getByName(name);
    }

    public List<Genre> getGenreByName(String name) throws SQLException, ParseException {
        return genreDAO.getByName(name);
    }

    public List<Film> getFilmByName(String name) throws SQLException, ParseException {
        return filmDAO.getByName(name);
    }
    //endregion SEARCH

    //region FILTER
    @Override
    public List<Producer> getProducerByFilter(ProducerFilter producerFilter) throws SQLException, ParseException {
        return producerDAO.getByFilter(producerFilter);
    }

    @Override
    public List<Franchise> getFranchiseByFilter(FranchiseFilter franchiseFilter) throws SQLException, ParseException {
        return franchiseDAO.getByFilter(franchiseFilter);
    }

    @Override
    public List<Genre> getGenreByFilter(GenreFilter genreFilter) throws SQLException, ParseException {
        return genreDAO.getByFilter(genreFilter);
    }

    @Override
    public List<Film> getFilmByFilter(FilmFilter filmFilter) throws SQLException, ParseException {
        return filmDAO.getByFilter(filmFilter);
    }
    //endregion FILTER
}
