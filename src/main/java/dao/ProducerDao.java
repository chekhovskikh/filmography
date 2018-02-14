package dao;

import entitiy.Producer;
import util.dbdriver.DbManager;
import entitiy.filter.ProducerFilter;
import entitiy.filter.EntityFilter;
import util.EntityUtils;
import util.dbdriver.PostgresManager;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProducerDao implements EntityDao<Producer> {
    private Connection connection;

    public ProducerDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new PostgresManager(username, password);
        connection = manager.connect(host, port, sid);
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Producer> getAll() throws SQLException, ParseException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id");
        List<Producer> producers = new ArrayList<>();
        while (set.next())
            producers.add(new Producer(set.getInt("producer_id"),
                    set.getString("producer_name"),
                    set.getString("country_name"),
                    EntityUtils.parseDate(set.getString("birthdate"))));
        return producers;
    }

    public Producer get(int id) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id " +
                        "WHERE producer_id = ?");
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        set.next();
        return new Producer(set.getInt("producer_id"),
                set.getString("producer_name"),
                set.getString("country_name"),
                EntityUtils.parseDate(set.getString("birthdate")));
    }

    public void add(Producer producer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO COUNTRY(country_name) SELECT ? " +
                "WHERE NOT EXISTS (SELECT country_name FROM COUNTRY WHERE country_name = ?)");
        statement.setString(1, producer.getCitizenship());
        statement.setString(2, producer.getCitizenship());
        statement.executeUpdate();

        statement = connection.prepareStatement("INSERT INTO producer(producer_name, citizenship_id, birthdate) " +
                "SELECT ?, country.country_id, to_date(?,'yyyy-MM-dd') FROM country WHERE country_name = ?");
        statement.setString(1, producer.getProducerName());
        statement.setString(2, producer.dateToString());
        statement.setString(3, producer.getCitizenship());
        statement.executeUpdate();
    }

    public void addAll(List<Producer> producers) throws SQLException {
        for (Producer producer : producers) {
            add(producer);
        }
    }

    public void remove(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM producer WHERE producer_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void update(Producer producer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO COUNTRY(country_name) SELECT ? " +
                "WHERE NOT EXISTS (SELECT country_name FROM COUNTRY WHERE country_name = ?)");
        statement.setString(1, producer.getCitizenship());
        statement.setString(2, producer.getCitizenship());
        statement.executeUpdate();

        statement = connection.prepareStatement("SELECT country_id FROM country WHERE country_name = ?");
        statement.setString(1, producer.getCitizenship());
        ResultSet set = statement.executeQuery();
        set.next();
        int countryId = set.getInt("country_id");

        statement = connection.prepareStatement("UPDATE producer SET producer_name = ?, " +
                "birthdate = to_date(?, 'yyyy-MM-dd'), citizenship_id = ? WHERE producer_id = ?");
        statement.setString(1, producer.getProducerName());
        statement.setString(2, producer.dateToString());
        statement.setInt(3, countryId);
        statement.setInt(4, producer.getId());
        statement.executeUpdate();
    }

    public List<Producer> getByName(String name) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id WHERE " +
                "UPPER(producer.producer_name) LIKE UPPER(?)");
        statement.setString(1, "%" + name + "%");
        ResultSet set = statement.executeQuery();
        List<Producer> producers = new ArrayList<>();
        while (set.next())
            producers.add(new Producer(set.getInt("producer_id"),
                    set.getString("producer_name"),
                    set.getString("country_name"),
                    EntityUtils.parseDate(set.getString("birthdate"))));
        return producers;
    }

    public List<Producer> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        ProducerFilter filter = (ProducerFilter) entityFilter;
        String endQuery = "";
        Boolean isFilteringDate = !EntityUtils.formatTime(filter.getBirthdate()).equals("00:59:59");
        if (isFilteringDate)
            endQuery = "AND TO_CHAR(producer.birthdate,'yyyy-MM-dd') = ?";

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM producer " +
                "JOIN country citizenship ON citizenship.country_id = producer.citizenship_id WHERE " +
                "UPPER(producer.producer_name) LIKE UPPER(?) AND " +
                "UPPER(citizenship.country_name) LIKE UPPER(?) " + endQuery);
        statement.setString(1, "%" + filter.getName() + "%");
        statement.setString(2, "%" + filter.getCitizenship() + "%");
        if (isFilteringDate) {
            //noinspection JpaQueryApiInspection
            statement.setString(3, filter.dateToString());
        }
        ResultSet set = statement.executeQuery();
        List<Producer> producers = new ArrayList<>();
        while (set.next())
            producers.add(new Producer(set.getInt("producer_id"),
                    set.getString("producer_name"),
                    set.getString("country_name"),
                    EntityUtils.parseDate(set.getString("birthdate"))));
        return producers;
    }
}
