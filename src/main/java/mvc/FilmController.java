package mvc;

import com.google.inject.Inject;
import entitiy.*;

import java.sql.SQLException;
import java.util.List;

public class FilmController implements Controller {

    private Model model;

    @Inject
    public FilmController(Model model) {
        this.model = model;
    }

    public Model getModel(){
        return model;
    }


    public void addGenre(Genre genre) throws SQLException {
        model.addGenre(genre);
    }

    public void addProducer(Producer producer) throws SQLException {
        model.addProducer(producer);
    }

    public void addFranchise(Franchise franchise) throws SQLException {
        model.addFranchise(franchise);
    }

    public void addFilm(Film film) throws SQLException {
        model.addFilm(film);
    }

    public void addGenres(List<Genre> genres) throws SQLException {
        model.addGenres(genres);
    }

    public void addProducers(List<Producer> producers) throws SQLException {
        model.addProducers(producers);
    }

    public void addFranchises(List<Franchise> franchises) throws SQLException {
        model.addFranchises(franchises);
    }

    public void addFilms(List<Film> films) throws SQLException {
        model.addFilms(films);
    }

    public void removeGenre(int id) throws SQLException {
        model.removeGenre(id);
    }

    public void removeProducer(int id) throws SQLException {
        model.removeProducer(id);
    }

    public void removeFranchise(int id) throws SQLException {
        model.removeFranchise(id);
    }

    public void removeFilm(int id) throws SQLException {
        model.removeFilm(id);
    }

    public void updateFranchise(Franchise updatedFranchise) throws SQLException{
        model.updateFranchise(updatedFranchise);
    }

    public void updateProducer(Producer updatedProducer) throws SQLException{
        model.updateProducer(updatedProducer);
    }

    public void updateGenre(Genre updatedGenre) throws SQLException{
        model.updateGenre(updatedGenre);
    }

    public void updateFilm(Film updatedFilm) throws SQLException{
        model.updateFilm(updatedFilm);
    }

    /*
    public void save(String fileLocation) throws IOException {
        model.save(fileLocation);
    }

    public void open(String fileLocation) throws IOException {
        model.open(fileLocation);
    }
    */
}
