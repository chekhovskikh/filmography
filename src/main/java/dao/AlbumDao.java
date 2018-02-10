package dao;

import entities.Album;
import dbdrivers.DbManager;
import dbdrivers.OracleManager;
import entities.filters.AlbumFilter;
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

public class AlbumDao implements EntityDao<Album> {
    private Connection connection;
    private Statement statement;
    private List<Album> albums;

    public AlbumDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new OracleManager(username, password);
        connection = manager.connect(host, port, sid);
        statement = connection.createStatement();
        albums = new ArrayList<>();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Album> getAll() throws SQLException, ParseException {
        albums.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM album");
        while (set.next())
            albums.add(new Album(set.getInt("id"),
                    set.getString("name"),
                    set.getInt("band_id"),
                    EntityUtils.parseFullDate(set.getString("release"))));
        return albums;
    }

    public Album get(int id) throws SQLException, ParseException {
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM album WHERE id=" + id);
        set.next();
        return new Album(set.getInt("id"),
                set.getString("name"),
                set.getInt("band_id"),
                EntityUtils.parseFullDate(set.getString("release")));
    }

    public void add(Album album) throws SQLException {
        statement.executeUpdate("INSERT INTO album(name,release,band_id) values('" + album.getName() +
                "',to_date('" + album.dateToString() + "', 'yyyy-MM-dd')," + album.getBandId() + ")");
    }

    public void addAll(List<Album> albums) throws SQLException {
        String sqlRequest = "INSERT INTO album(name,release,band_id) values ";
        for (int i = 0, size = albums.size(); i < size; i++) {
            Album album = albums.get(i);
            sqlRequest += "('" + album.getName() +
                    "',to_date('" + album.dateToString() +
                    "', 'dd.mm.yyyy')," + album.getBandId() + ")";
            if (i != size - 1)
                sqlRequest += ",";
        }
        statement.executeUpdate(sqlRequest);
    }

    public void remove(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM album WHERE id=" + id);
    }

    public void update(Album album) throws SQLException {
        statement.executeUpdate("UPDATE album " +
                "SET name='" + album.getName() +
                "', release=to_date('" + album.dateToString() + "', 'yyyy-MM-dd')" +
                ", band_id=" + album.getBandId() +
                " WHERE id=" + album.getId());
    }

    public List<Album> getByName(String name) throws SQLException, ParseException {
        albums.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM album WHERE " + regexpLike("album.name", name));
        while (set.next())
            albums.add(new Album(set.getInt("id"),
                    set.getString("name"),
                    set.getInt("band_id"),
                    EntityUtils.parseFullDate(set.getString("release"))));
        return albums;
    }

    public List<Album> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        AlbumFilter filter = (AlbumFilter) entityFilter;
        albums.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM album " +
                "LEFT OUTER JOIN band ON band.ID = album.BAND_ID WHERE " +
                (EntityUtils.formatTime(filter.getRelease()).equals("59:59") ? "" : "TO_CHAR(album.release,'yyyy-MM-dd') = '" + filter.dateToString() + "' AND ") +
                regexpLike("album.name", filter.getName()) + " AND " +
                regexpLike("band.name", filter.getBandName()));
        while (set.next())
            albums.add(new Album(set.getInt("id"),
                    set.getString("name"),
                    set.getInt("band_id"),
                    EntityUtils.parseFullDate(set.getString("release"))));
        return albums;
    }

    private String regexpLike(String attribute, String value){
        return "REGEXP_LIKE(" + attribute + ", '(^| |-)+" + value + "\\w*','i')";
    }
}
