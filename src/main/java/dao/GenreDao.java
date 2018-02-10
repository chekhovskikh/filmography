package dao;

import entitiy.Genre;
import util.dbdrivers.DbManager;
import entitiy.filter.EntityFilter;
import entitiy.filter.GenreFilter;
import util.dbdrivers.PostgresManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GenreDao implements EntityDao<Genre> {
    private Connection connection;
    private Statement statement;
    private List<Genre> genres;

    public GenreDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new PostgresManager(username, password);
        connection = manager.connect(host, port, sid);
        statement = connection.createStatement();
        genres = new ArrayList<>();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Genre> getAll() throws SQLException {
        genres.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM genre");
        while (set.next())
            genres.add(new Genre(set.getInt("genre_id"),
                    set.getString("genre_name"),
                    set.getInt("parent_id")));
        return genres;
    }

    public Genre get(int id) throws SQLException {
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM genre WHERE genre_id=" + id);
        set.next();
        return new Genre(set.getInt("genre_id"),
                set.getString("genre_name"),
                set.getInt("parent_id"));
    }

    public void add(Genre genre) throws SQLException {
        statement.executeUpdate("INSERT INTO genre(genre_name, parent_id) values('"
                + genre.getGenreName() + "',"
                + (genre.getParentId() == 0 ? "null" : genre.getParentId()) + ")");
    }

    public void addAll(List<Genre> genres) throws SQLException {
        String sqlRequest = "INSERT INTO genre(genre_name, parent_id) values ";
        for (int i = 0, size = genres.size(); i < size; i++) {
            Genre genre = genres.get(i);
            sqlRequest += "('" + genre.getGenreName() + "',"
                    + (genre.getParentId() == 0 ? "null" : genre.getParentId()) + ")";
            if (i != size - 1)
                sqlRequest += ",";
        }
        statement.executeUpdate(sqlRequest);
    }

    public void remove(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM genre WHERE genre_id=" + id);
    }

    public void update(Genre genre) throws SQLException {
        statement.executeUpdate("UPDATE genre " +
                "SET genre_name='" + genre.getGenreName() +
                "', parent_id=" + (genre.getParentId() == 0 ? "null" : genre.getParentId()) +
                " WHERE genre_id=" + genre.getId());
    }

    public List<Genre> getByName(String name) throws SQLException, ParseException {
        genres.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM genre WHERE " +
                regexpLike("genre.genre_name", name));
        while (set.next())
            genres.add(new Genre(set.getInt("genre_id"),
                    set.getString("genre_name"),
                    set.getInt("parent_id")));
        return genres;
    }

    public List<Genre> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        GenreFilter filter = (GenreFilter) entityFilter;
        genres.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM genre " +
                "FULL JOIN genre parent ON parent.genre_id = genre.parent_id WHERE " +
                regexpLike("genre.genre_name", filter.getName()) + " AND " +
                regexpLike("parent.genre_name", filter.getParentName()));
        while (set.next())
            genres.add(new Genre(set.getInt("genre_id"),
                    set.getString("genre_name"),
                    set.getInt("parent_id")));
        return genres;
    }

    private String regexpLike(String attribute, String value){
        return "REGEXP_LIKE(" + attribute + ", '(^| |-)+" + value + "\\w*','i')";
    }
}
