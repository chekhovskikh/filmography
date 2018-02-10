package entities.filters;

import music.util.EntityUtils;

import java.util.Date;

public class BandFilter implements EntityFilter {
    private String name;
    private Date foundation;


    public BandFilter() {
    }

    public BandFilter(String name, Date foundation) {
        this.name = name;
        this.foundation = foundation;
    }

    public Date getFoundation() {
        return foundation;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String dateToString(){
        return EntityUtils.formatDate(foundation);
    }
}
