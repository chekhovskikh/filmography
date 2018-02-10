package mvc;

import entitiy.*;

import java.sql.SQLException;
import java.util.List;

public interface Controller {

    Model getModel();

    void addGenre(Genre genre) throws SQLException;

    void addProducer(Producer producer) throws SQLException;

    void addFranchise(Franchise franchise) throws SQLException;

    void addFilm(Film film) throws SQLException;

    void addGenres(List<Genre> genres) throws SQLException;

    void addProducers(List<Producer> producers) throws SQLException;

    void addFranchises(List<Franchise> franchises) throws SQLException;

    void addFilms(List<Film> films) throws SQLException;

    void removeGenre(int id) throws SQLException;

    void removeProducer(int id) throws SQLException;

    void removeFranchise(int id) throws SQLException;

    void removeFilm(int id) throws SQLException;

    void updateFranchise(Franchise updatedFranchise) throws SQLException;

    void updateProducer(Producer updatedProducer) throws SQLException;

    void updateGenre(Genre updatedGenre) throws SQLException;

    void updateFilm(Film updatedFilm) throws SQLException;

    //void save(String fileLocation) throws IOException;

    //void open(String fileLocation) throws IOException;
}
