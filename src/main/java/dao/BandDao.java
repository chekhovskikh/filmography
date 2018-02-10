package dao;

import entities.Band;
import dbdrivers.DbManager;
import dbdrivers.OracleManager;
import entities.filters.BandFilter;
import entities.filters.EntityFilter;
import music.util.EntityUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BandDao implements EntityDao<Band> {
    private Connection connection;
    private Statement statement;
    private List<Band> bands;

    public BandDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new OracleManager(username, password);
        connection = manager.connect(host, port, sid);
        statement = connection.createStatement();
        bands = new ArrayList<>();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Band> getAll() throws SQLException, ParseException {
        bands.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM band");
        while (set.next())
            bands.add(new Band(set.getInt("id"),
                    set.getString("name"),
                    EntityUtils.parseFullDate(set.getString("DATE_OF_FOUNDATION"))));
        return bands;
    }

    public Band get(int id) throws SQLException, ParseException {
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM band WHERE id=" + id);
        set.next();
        return new Band(set.getInt("id"),
                set.getString("name"),
                EntityUtils.parseFullDate(set.getString("DATE_OF_FOUNDATION")));
    }

    public void add(Band band) throws SQLException {
        statement.executeUpdate("INSERT INTO band(name,DATE_OF_FOUNDATION) values('" + band.getName() +
                "',to_date('" + band.dateToString() + "', 'yyyy-MM-dd'))");
    }

    public void addAll(List<Band> bands) throws SQLException {
        String sqlRequest = "INSERT INTO band(name,DATE_OF_FOUNDATION) values ";
        for (int i = 0, size = bands.size(); i < size; i++) {
            Band band = bands.get(i);
            sqlRequest += "('" + band.getName() +
                    "',to_date('" + band.dateToString() + "', 'yyyy-MM-dd'))";
            if (i != size - 1)
                sqlRequest += ",";
        }
        statement.executeUpdate(sqlRequest);
    }

    public void remove(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM band WHERE id=" + id);
    }

    public void update(Band band) throws SQLException {
        statement.executeUpdate("UPDATE band " + "SET name='" + band.getName() +
                "', DATE_OF_FOUNDATION=to_date('" + band.dateToString() + "', 'yyyy-MM-dd')" +
                " WHERE id=" + band.getId());
    }

    public List<Band> getByName(String name) throws SQLException, ParseException {
        bands.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM band WHERE " +
                "REGEXP_LIKE(band.name, '(^| |-)+" + name + "\\w*','i')");
        while (set.next())
            bands.add(new Band(set.getInt("id"),
                    set.getString("name"),
                    EntityUtils.parseFullDate(set.getString("DATE_OF_FOUNDATION"))));
        return bands;
    }

    public List<Band> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        BandFilter filter = (BandFilter) entityFilter;
        bands.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM band WHERE " +
                (EntityUtils.formatTime(filter.getFoundation()).equals("59:59") ? "" : "TO_CHAR(band.DATE_OF_FOUNDATION,'yyyy-MM-dd') = '" + filter.dateToString() + "' AND ") +
                regexpLike("band.name", filter.getName()));
        while (set.next())
            bands.add(new Band(set.getInt("id"),
                    set.getString("name"),
                    EntityUtils.parseFullDate(set.getString("DATE_OF_FOUNDATION"))));
        return bands;
    }

    private String regexpLike(String attribute, String value){
        return "REGEXP_LIKE(" + attribute + ", '(^| |-)+" + value + "\\w*','i')";
    }
}
