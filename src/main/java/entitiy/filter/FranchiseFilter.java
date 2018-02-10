package entitiy.filter;

import util.EntityUtils;

import java.util.Date;

public class FranchiseFilter implements EntityFilter {
    private String franchiseName;
    private String country;
    private Date release;

    public FranchiseFilter() {
    }

    public FranchiseFilter(String franchiseName, String country, Date release) {
        this.franchiseName = franchiseName;
        this.country = country;
        this.release = release;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public String getName() {
        return franchiseName;
    }

    public void setName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String dateToString(){
        return EntityUtils.formatDate(release);
    }
}
