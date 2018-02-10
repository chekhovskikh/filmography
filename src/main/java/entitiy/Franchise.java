package entitiy;

import util.EntityUtils;

import java.util.Date;

public class Franchise implements Entity {
    private int franchiseId;
    private String franchiseName;
    private String country;
    private Date release;

    public Franchise() {
    }

    public Franchise(int franchiseId) {
        this.franchiseId = franchiseId;
    }

    public Franchise(String franchiseName, String country, Date release) {
        this.franchiseName = franchiseName;
        this.country = country;
        this.release = release;
    }

    public Franchise(int franchiseId, String franchiseName, String country, Date release) {
        this.franchiseId = franchiseId;
        this.franchiseName = franchiseName;
        this.country = country;
        this.release = release;
    }

    public int getId() {
        return franchiseId;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String dateToString(){
        return EntityUtils.formatDate(release);
    }

    public String defaultDateToString() {
        return EntityUtils.defaultFormatDate(release);
    }
}
