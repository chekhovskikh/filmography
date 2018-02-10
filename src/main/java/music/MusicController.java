package music;

import com.google.inject.Inject;
import entities.*;

import javax.jws.WebParam;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class MusicController implements Controller {

    private Model model;

    @Inject
    public MusicController(Model model) {
        this.model = model;
    }

    public Model getModel(){
        return model;
    }


    public void addGenre(Genre genre) throws SQLException {
        model.addGenre(genre);
    }

    public void addBand(Band band) throws SQLException {
        //if (band.equals(model.getBands()))
            //throw new DuplicateException("Found duplicate Band");
        model.addBand(band);
    }

    public void addAlbum(Album album) throws SQLException {
        model.addAlbum(album);
    }

    public void addSong(Song song) throws SQLException {
        model.addSong(song);
    }

    public void addGenres(List<Genre> genres) throws SQLException {
        model.addGenres(genres);
    }

    public void addBands(List<Band> bands) throws SQLException {
        model.addBands(bands);
    }

    public void addAlbums(List<Album> albums) throws SQLException {
        model.addAlbums(albums);
    }

    public void addSongs(List<Song> songs) throws SQLException {
        model.addSongs(songs);
    }

    public void removeGenre(int id) throws SQLException {
        model.removeGenre(id);
    }

    public void removeBand(int id) throws SQLException {
        model.removeBand(id);
    }

    public void removeAlbum(int id) throws SQLException {
        model.removeAlbum(id);
    }

    public void removeSong(int id) throws SQLException {
        model.removeSong(id);
    }

    public void updateAlbum(Album updatedAlbum) throws SQLException{
        model.updateAlbum(updatedAlbum);
    }

    public void updateBand(Band updatedBand) throws SQLException{
        model.updateBand(updatedBand);
    }

    public void updateGenre(Genre updatedGenre) throws SQLException{
        model.updateGenre(updatedGenre);
    }

    public void updateSong(Song updatedSong) throws SQLException{
        model.updateSong(updatedSong);
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
