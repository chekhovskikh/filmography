package entitiy.filter;

import util.EntityUtil;

import java.util.Date;

public class ProducerFilter implements EntityFilter {
    private String producerName;
    private String citizenship;
    private Date birthdate;


    public ProducerFilter() {
    }

    public ProducerFilter(String name, String citizenship, Date birthdate) {
        this.producerName = name;
        this.citizenship = citizenship;
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getName() {
        return producerName;
    }

    public void setName(String producerName) {
        this.producerName = producerName;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String dateToString(){
        return EntityUtil.formatDate(birthdate);
    }
}
