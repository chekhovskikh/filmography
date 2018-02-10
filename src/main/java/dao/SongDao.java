package dao;

import entities.Song;
import dbdrivers.DbManager;
import dbdrivers.OracleManager;
import entities.filters.EntityFilter;
import entities.filters.SongFilter;
import music.util.EntityUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SongDao implements EntityDao<Song> {
    private Connection connection;
    private Statement statement;
    private List<Song> songs;

    public SongDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new OracleManager(username, password);
        connection = manager.connect(host, port, sid);
        statement = connection.createStatement();
        songs = new ArrayList<>();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Song> getAll() throws SQLException, ParseException {
        songs.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM song");
        while (set.next())
            songs.add(new Song(set.getInt("id"),
                    set.getString("name"),
                    EntityUtils.parseFullDate(set.getString("time")),
                    set.getInt("band_id"),
                    set.getInt("album_id"),
                    set.getInt("genre_id")));
        return songs;
    }

    public Song get(int id) throws SQLException, ParseException {
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM song WHERE id=" + id);
        set.next();
        return new Song(set.getInt("id"),
                set.getString("name"),
                EntityUtils.parseFullDate(set.getString("time")),
                set.getInt("band_id"),
                set.getInt("album_id"),
                set.getInt("genre_id"));
    }

    public void add(Song song) throws SQLException {
        statement.executeUpdate("INSERT INTO song(name,time,genre_id,band_id,album_id) values('" + song.getName() +
                "',to_date('" + song.timeToString() + "', 'MI:SS')," +
                (song.getGenreId() == 0 ? "null" : song.getGenreId()) + "," +
                song.getBandId() + "," + (song.getAlbumId() == 0 ? "null" : song.getAlbumId()) + ")");
    }

    public void addAll(List<Song> songs) throws SQLException {
        String sqlRequest = "INSERT INTO song(name,time,genre_id,band_id,album_id) values ";
        for (int i = 0, size = songs.size(); i < size; i++) {
            Song song = songs.get(i);
            sqlRequest += "('" + song.getName() +
                    "',to_date('" + song.timeToString() + "', 'MI:SS')," +
                    (song.getGenreId() == 0 ? "null" : song.getGenreId()) + "," +
                    song.getBandId() + "," + (song.getAlbumId() == 0 ? "null" : song.getAlbumId()) + ")";
            if (i != size - 1)
                sqlRequest += ",";
        }
        statement.executeUpdate(sqlRequest);
    }

    public void remove(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM song WHERE id=" + id);
    }

    public void update(Song song) throws SQLException {
        statement.executeUpdate("UPDATE song " +
                "SET name='" + song.getName() +
                "', time=to_date('" + song.timeToString() + "', 'MI:SS')" +
                ", genre_id=" + (song.getGenreId() == 0 ? "null" : song.getGenreId()) +
                ", band_id=" + song.getBandId() +
                ", album_id=" + (song.getAlbumId() == 0 ? "null" : song.getAlbumId()) +
                " WHERE id=" + song.getId());
    }

    public List<Song> getByName(String name) throws SQLException, ParseException {
        songs.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM song WHERE " + regexpLike("song.name", name));
        while (set.next())
            songs.add(new Song(set.getInt("id"),
                    set.getString("name"),
                    EntityUtils.parseFullDate(set.getString("time")),
                    set.getInt("band_id"),
                    set.getInt("album_id"),
                    set.getInt("genre_id")));
        return songs;
    }

    public List<Song> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        SongFilter filter = (SongFilter) entityFilter;
        songs.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM song " +
                "LEFT OUTER JOIN band ON song.BAND_ID = band.ID " +
                "LEFT OUTER JOIN genre ON song.GENRE_ID = genre.ID " +
                "LEFT OUTER JOIN album ON song.ALBUM_ID = album.ID WHERE " +
                (filter.timeToString().equals("00:00") ? "" : "TO_CHAR(song.time,'MI:SS') = '" + filter.timeToString() + "' AND ") +
                regexpLike("song.name", filter.getName()) + " AND " +
                regexpLike("band.name", filter.getBandName()) + " AND " +
                regexpLike("genre.name", filter.getGenreName()) + " AND " +
                regexpLike("album.name", filter.getAlbumName()));
        while (set.next())
            songs.add(new Song(set.getInt("id"),
                    set.getString("name"),
                    EntityUtils.parseFullDate(set.getString("time")),
                    set.getInt("band_id"),
                    set.getInt("album_id"),
                    set.getInt("genre_id")));
        return songs;
    }

    private String regexpLike(String attribute, String value){
        return "REGEXP_LIKE(" + attribute + ", '(^| |-)+" + value + "\\w*','i')";
    }
}
