package entities;

import music.util.EntityUtils;

import java.util.Date;

public class Album implements Entity {
    private int id;
    private String name;
    private int bandId;
    private Date release;

    public Album() {
    }

    public Album(int id) {
        this.id = id;
    }

    public Album(String name, int bandId, Date release) {
        this.name = name;
        this.bandId = bandId;
        this.release = release;
    }

    public Album(int id, String name, int bandId, Date release) {
        this.id = id;
        this.name = name;
        this.bandId = bandId;
        this.release = release;
    }

    public int getId() {
        return id;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String dateToString(){
        return EntityUtils.formatDate(release);
    }

    public String defaultDateToString() {
        return EntityUtils.defaultFormatDate(release);
    }
}
