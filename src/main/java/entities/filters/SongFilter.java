package entities.filters;


import music.util.EntityUtils;

import java.util.Date;

public class SongFilter implements EntityFilter {
    private String name;
    private Date time;
    private String bandName;
    private String albumName;
    private String genreName;

    public SongFilter() {
    }

    public SongFilter(String name, Date time, String bandName, String albumName, String genreName) {
        this.name = name;
        this.time = time;
        this.bandName = bandName;
        this.albumName = albumName;
        this.genreName = genreName;
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

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String timeToString(){
        return EntityUtils.formatTime(time);
    }
}
