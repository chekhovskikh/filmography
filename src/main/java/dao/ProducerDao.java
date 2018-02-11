package dao;

import entitiy.Producer;
import util.dbdrivers.DbManager;
import entitiy.filter.ProducerFilter;
import entitiy.filter.EntityFilter;
import util.EntityUtils;
import util.dbdrivers.PostgresManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProducerDao implements EntityDao<Producer> {
    private Connection connection;
    private Statement statement;
    private List<Producer> producers;

    public ProducerDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new PostgresManager(username, password);
        connection = manager.connect(host, port, sid);
        statement = connection.createStatement();
        producers = new ArrayList<>();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Producer> getAll() throws SQLException, ParseException {
        producers.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id");
        while (set.next())
            producers.add(new Producer(set.getInt("producer_id"),
                    set.getString("producer_name"),
                    set.getString("country_name"),
                    EntityUtils.parseDate(set.getString("birthdate"))));
        return producers;
    }

    public Producer get(int id) throws SQLException, ParseException {
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id " +
                "WHERE producer_id=" + id);
        set.next();
        return new Producer(set.getInt("producer_id"),
                set.getString("producer_name"),
                set.getString("country_name"),
                EntityUtils.parseDate(set.getString("birthdate")));
    }

    public void add(Producer producer) throws SQLException {
        try {
            statement.executeUpdate("INSERT INTO country(country_name) values('" + producer.getCitizenship() + "')");
        }
        catch (Exception e){ }

        statement.executeUpdate("INSERT INTO producer(producer_name, citizenship_id, birthdate) SELECT '" +
                producer.getProducerName() + "', country.country_id, " +
                "to_date('" + producer.dateToString() + "', 'yyyy-MM-dd')" +
                " FROM country WHERE country_name='" + producer.getCitizenship() + "'");
    }

    public void addAll(List<Producer> producers) throws SQLException {
        String sqlCountryRequest = "INSERT INTO country(country_name) values ";
        for (int i = 0, size = producers.size(); i < size; i++) {
            Producer producer = producers.get(i);
            sqlCountryRequest += "('" + producer.getCitizenship() + "')";
            if (i != size - 1)
                sqlCountryRequest += ",";
        }
        try {
            statement.executeUpdate(sqlCountryRequest);
        } catch (Exception e) { }
        for (Producer producer : producers) {
            statement.executeUpdate("INSERT INTO producer(producer_name, citizenship_id, birthdate) SELECT '" +
                    producer.getProducerName() + "', country.country_id, " +
                    "to_date('" + producer.dateToString() + "', 'yyyy-MM-dd'))" +
                    " FROM country WHERE country_name='" + producer.getCitizenship() + "'");
        }
    }

    public void remove(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM producer WHERE producer_id=" + id);
    }

    public void update(Producer producer) throws SQLException {
        try {
            statement.executeUpdate("INSERT country(country_name) values(" +
                    "country_name='" + producer.getCitizenship() + "')");
        } catch (Exception e) { }
        ResultSet set = statement.executeQuery("SELECT country_id FROM country WHERE country_name='" + producer.getCitizenship() + "'");
        set.next();
        int countryId = set.getInt("country_id");

        statement.executeUpdate("UPDATE producer " +
                "SET producer_name='" + producer.getProducerName() +
                "', birthdate=to_date('" + producer.dateToString() + "', 'yyyy-MM-dd')" +
                ", citizenship_id=" + countryId +
                " WHERE producer_id=" + producer.getId());
    }

    public List<Producer> getByName(String name) throws SQLException, ParseException {
        producers.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id WHERE " +
                regexpLike("producer.producer_name", name));
        while (set.next())
            producers.add(new Producer(set.getInt("producer_id"),
                    set.getString("producer_name"),
                    set.getString("country_name"),
                    EntityUtils.parseDate(set.getString("birthdate"))));
        return producers;
    }

    public List<Producer> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        ProducerFilter filter = (ProducerFilter) entityFilter;
        producers.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id WHERE " +
                (EntityUtils.formatTime(filter.getBirthdate()).equals("59:59") ? "" : "TO_CHAR(producer.birthdate,'yyyy-MM-dd') = '" + filter.dateToString() + "' AND ") +
                regexpLike("producer.producer_name", filter.getName()) + " AND " +
                regexpLike("citizenship.country_name", filter.getCitizenship()));
        while (set.next())
            producers.add(new Producer(set.getInt("producer_id"),
                    set.getString("producer_name"),
                    set.getString("country_name"),
                    EntityUtils.parseDate(set.getString("birthdate"))));
        return producers;
    }

    private String regexpLike(String attribute, String value){
        return "UPPER(" + attribute + ") LIKE UPPER('%" + value + "%')";
    }
}
