package mvc;

import entitiy.Franchise;
import entitiy.Producer;
import entitiy.Genre;
import entitiy.Film;
import entitiy.filter.FranchiseFilter;
import entitiy.filter.ProducerFilter;
import entitiy.filter.GenreFilter;
import entitiy.filter.FilmFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Observer;

public interface Model {

    void close() throws SQLException;

    List<Franchise> getFranchises() throws SQLException, ParseException;

    Franchise getFranchise(int id) throws SQLException, ParseException;

    List<Producer> getProducers() throws ParseException, SQLException;

    Producer getProducer(int id) throws SQLException, ParseException;

    List<Genre> getGenres() throws SQLException, ParseException;

    Genre getGenre(int id) throws SQLException, ParseException;

    List<Film> getFilms() throws SQLException, ParseException;

    Film getFilm(int id) throws SQLException, ParseException;

    void addFranchise(Franchise franchise) throws SQLException;

    void addFranchises(List<Franchise> franchises) throws SQLException;

    void addProducer(Producer producer) throws SQLException;

    void addProducers(List<Producer> producers) throws SQLException;

    void addGenre(Genre genre) throws SQLException;

    void addGenres(List<Genre> genres) throws SQLException;

    void addFilm(Film film) throws SQLException;

    void addFilms(List<Film> films) throws SQLException;

    void removeFranchise(int id) throws SQLException;

    void removeProducer(int id) throws SQLException;

    void removeGenre(int id) throws SQLException;

    void removeFilm(int id) throws SQLException;

    //void save(String fileLocation) throws IOException;

    //void open(String fileLocation) throws IOException;

    void updateFranchise(Franchise updatedFranchise) throws SQLException;

    void updateProducer(Producer updatedProducer) throws SQLException;

    void updateGenre(Genre updatedGenre) throws SQLException;

    void updateFilm(Film updatedFilm) throws SQLException;

    List<Producer> getProducerByName(String name) throws SQLException, ParseException;

    List<Franchise> getFranchiseByName(String name) throws SQLException, ParseException;

    List<Genre> getGenreByName(String name) throws SQLException, ParseException;

    List<Film> getFilmByName(String name) throws SQLException, ParseException;

    List<Producer> getProducerByFilter(ProducerFilter producerFilter) throws SQLException, ParseException;

    List<Franchise> getFranchiseByFilter(FranchiseFilter franchiseFilter) throws SQLException, ParseException;

    List<Genre> getGenreByFilter(GenreFilter genreFilter) throws SQLException, ParseException;

    List<Film> getFilmByFilter(FilmFilter filmFilter) throws SQLException, ParseException;

    void addObserver(Observer o);

    void deleteObserver(Observer o);
}
