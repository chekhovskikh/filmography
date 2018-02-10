package entities.filters;

import music.util.EntityUtils;

import java.util.Date;

public class AlbumFilter implements EntityFilter {
    private String name;
    private String bandName;
    private Date release;

    public AlbumFilter() {
    }

    public AlbumFilter(String name, String bandName, Date release) {
        this.name = name;
        this.bandName = bandName;
        this.release = release;
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

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String dateToString(){
        return EntityUtils.formatDate(release);
    }
}
