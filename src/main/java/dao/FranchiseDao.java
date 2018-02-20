package dao;

import entitiy.Franchise;
import util.EntityUtil;
import util.dbdriver.DbManager;
import entitiy.filter.FranchiseFilter;
import entitiy.filter.EntityFilter;
import util.dbdriver.PostgresManager;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FranchiseDao implements EntityDao<Franchise> {
    private Connection connection;

    public FranchiseDao(Properties properties) throws SQLException {
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

    public List<Franchise> getAll() throws SQLException, ParseException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM franchise " +
                "JOIN country ON country.country_id = franchise.country_id");
        List<Franchise> franchises = new ArrayList<>();
        while (set.next())
            franchises.add(new Franchise(set.getInt("franchise_id"),
                    set.getString("franchise_name"),
                    set.getString("country_name"),
                    EntityUtil.parseDate(set.getString("release"))));
        return franchises;
    }

    public Franchise get(int id) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM franchise " +
                "JOIN country ON country.country_id = franchise.country_id " +
                "WHERE franchise_id = ?");
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        set.next();
        return new Franchise(set.getInt("franchise_id"),
                set.getString("franchise_name"),
                set.getString("country_name"),
                EntityUtil.parseDate(set.getString("release")));
    }

    public void add(Franchise franchise) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO COUNTRY(country_name) SELECT ? " +
                "WHERE NOT EXISTS (SELECT country_name FROM COUNTRY WHERE country_name = ?)");
        statement.setString(1, franchise.getCountry());
        statement.setString(2, franchise.getCountry());
        statement.executeUpdate();

        statement = connection.prepareStatement("INSERT INTO franchise(franchise_name, country_id, release) " +
                "SELECT ?, country.country_id, to_date(?,'yyyy-MM-dd') FROM country WHERE country_name = ?");
        statement.setString(1, franchise.getFranchiseName());
        statement.setString(2, franchise.dateToString());
        statement.setString(3, franchise.getCountry());
        statement.executeUpdate();
    }

    public void addAll(List<Franchise> franchises) throws SQLException {
        for (Franchise franchise : franchises) {
            add(franchise);
        }
    }

    public void remove(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM franchise WHERE franchise_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void update(Franchise franchise) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO COUNTRY(country_name) SELECT ? " +
                "WHERE NOT EXISTS (SELECT country_name FROM COUNTRY WHERE country_name = ?)");
        statement.setString(1, franchise.getCountry());
        statement.setString(2, franchise.getCountry());
        statement.executeUpdate();

        statement = connection.prepareStatement("SELECT country_id FROM country WHERE country_name = ?");
        statement.setString(1, franchise.getCountry());
        ResultSet set = statement.executeQuery();
        set.next();
        int countryId = set.getInt("country_id");

        statement = connection.prepareStatement("UPDATE franchise SET franchise_name = ?, " +
                "release = to_date(?, 'yyyy-MM-dd'), country_id = ? WHERE franchise_id = ?");
        statement.setString(1, franchise.getFranchiseName());
        statement.setString(2, franchise.dateToString());
        statement.setInt(3, countryId);
        statement.setInt(4, franchise.getId());
        statement.executeUpdate();
    }

    public List<Franchise> getByName(String name) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM franchise " +
                "JOIN country ON country.country_id = franchise.country_id WHERE " +
                "UPPER(franchise.franchise_name) LIKE UPPER(?)");
        statement.setString(1, "%" + name + "%");
        ResultSet set = statement.executeQuery();
        List<Franchise> franchises = new ArrayList<>();
        while (set.next())
            franchises.add(new Franchise(set.getInt("franchise_id"),
                    set.getString("franchise_name"),
                    set.getString("country_name"),
                    EntityUtil.parseDate(set.getString("release"))));
        return franchises;
    }

    public List<Franchise> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        FranchiseFilter filter = (FranchiseFilter) entityFilter;
        String endQuery = "";
        Boolean isFilteringDate = !EntityUtil.formatTime(filter.getRelease()).equals("00:59:59");
        if (isFilteringDate)
            endQuery = "AND TO_CHAR(franchise.release,'yyyy-MM-dd') = ?";

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM franchise " +
                "JOIN country ON country.country_id = franchise.country_id WHERE " +
                "UPPER(franchise.franchise_name) LIKE UPPER(?) AND " +
                "UPPER(country.country_name) LIKE UPPER(?) " + endQuery);
        statement.setString(1, "%" + filter.getName() + "%");
        statement.setString(2, "%" + filter.getCountry() + "%");
        if (isFilteringDate) {
            //noinspection JpaQueryApiInspection
            statement.setString(3, filter.dateToString());
        }
        ResultSet set = statement.executeQuery();
        List<Franchise> franchises = new ArrayList<>();
        while (set.next())
            franchises.add(new Franchise(set.getInt("franchise_id"),
                    set.getString("franchise_name"),
                    set.getString("country_name"),
                    EntityUtil.parseDate(set.getString("release"))));
        return franchises;
    }
}