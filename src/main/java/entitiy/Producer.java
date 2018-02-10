package entitiy;

import util.EntityUtils;

import java.util.Date;

public class Producer implements Entity {
    private int producerId;
    private String producerName;
    private String citizenship;
    private Date birthdate;


    public Producer() {
    }

    public Producer(int producerId) {
        this.producerId = producerId;
    }

    public Producer(String producerName, String citizenship, Date birthdate) {
        this.producerId = producerId;
        this.producerName = producerName;
        this.birthdate = birthdate;
        this.citizenship = citizenship;
    }

    public Producer(int producerId, String producerName, String citizenship, Date birthdate) {
        this.producerId = producerId;
        this.producerName = producerName;
        this.birthdate = birthdate;
        this.citizenship = citizenship;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public int getId() {
        return producerId;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public void setId(int producerId) {
        this.producerId = producerId;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String dateToString() {
        return EntityUtils.formatDate(birthdate);
    }

    public String defaultDateToString() {
        return EntityUtils.defaultFormatDate(birthdate);
    }
}
