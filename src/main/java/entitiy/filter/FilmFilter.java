package entitiy.filter;


import util.EntityUtils;

import java.util.Date;

public class FilmFilter implements EntityFilter {
    private String filmName;
    private Date duration;
    private String producerName;
    private String franchiseName;
    private String genreName;

    public FilmFilter() {
    }

    public FilmFilter(String filmName, Date duration, String producerName, String franchiseName, String genreName) {
        this.filmName = filmName;
        this.duration = duration;
        this.producerName = producerName;
        this.franchiseName = franchiseName;
        this.genreName = genreName;
    }

    public String getName() {
        return filmName;
    }

    public void setName(String genreName) {
        this.filmName = genreName;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String timeToString(){
        return EntityUtils.formatTime(duration);
    }
}
