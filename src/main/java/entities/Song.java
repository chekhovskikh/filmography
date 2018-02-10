package entities;


import music.util.EntityUtils;

import java.util.Date;

public class Song implements Entity {
    private int id;
    private String name;
    private Date time;
    private int bandId;
    private int albumId;
    private int genreId;

    public Song() {
    }

    public Song(int id) {
        this.id = id;
    }

    public Song(String name, Date time, int bandId, int albumId, int genreId) {
        this.name = name;
        this.time = time;
        this.bandId = bandId;
        this.albumId = albumId;
        this.genreId = genreId;
    }

    public Song(int id, String name, Date time, int bandId, int albumId, int genreId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.bandId = bandId;
        this.albumId = albumId;
        this.genreId = genreId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String timeToString(){
        return EntityUtils.formatTime(time);
    }
}
