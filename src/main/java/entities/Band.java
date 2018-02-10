package entities;

import music.util.EntityUtils;

import java.util.Date;

public class Band implements Entity {
    private int id;
    private String name;
    private Date foundation;


    public Band() {
    }

    public Band(int id) {
        this.id = id;
    }

    public Band(String name, Date foundation) {
        this.name = name;
        this.foundation = foundation;
    }

    public Band(int id, String name, Date foundation) {
        this.id = id;
        this.name = name;
        this.foundation = foundation;
    }

    public Date getFoundation() {
        return foundation;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public String dateToString(){
        return EntityUtils.formatDate(foundation);
    }

    public String defaultDateToString() {
        return EntityUtils.defaultFormatDate(foundation);
    }
}
