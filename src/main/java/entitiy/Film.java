package entitiy;


import util.EntityUtils;

import java.util.Date;

public class Film implements Entity {
    private int filmId;
    private String filmName;
    private Date duration;
    private int producerId;
    private int franchiseId;
    private int genreId;

    public Film() {
    }

    public Film(int filmId) {
        this.filmId = filmId;
    }

    public Film(String filmName, Date duration, int producerId, int franchiseId, int genreId) {
        this.filmName = filmName;
        this.duration = duration;
        this.producerId = producerId;
        this.franchiseId = franchiseId;
        this.genreId = genreId;
    }

    public Film(int filmId, String filmName, Date duration, int producerId, int franchiseId, int genreId) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.duration = duration;
        this.producerId = producerId;
        this.franchiseId = franchiseId;
        this.genreId = genreId;
    }

    public int getId() {
        return filmId;
    }

    public void setId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String timeToString(){
        return EntityUtils.formatTime(duration);
    }
}
