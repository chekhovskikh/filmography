package dao;

import entitiy.Genre;
import util.dbdriver.DbManager;
import entitiy.filter.EntityFilter;
import entitiy.filter.GenreFilter;
import util.dbdriver.PostgresManager;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GenreDao implements EntityDao<Genre> {
    private Connection connection;

    public GenreDao(Properties properties) throws SQLException {
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

    public List<Genre> getAll() throws SQLException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM genre");
        List<Genre> genres = new ArrayList<>();
        while (set.next())
            genres.add(new Genre(set.getInt("genre_id"),
                    set.getString("genre_name"),
                    set.getInt("parent_id")));
        return genres;
    }

    public Genre get(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre_id = ?");
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        set.next();
        return new Genre(set.getInt("genre_id"),
                set.getString("genre_name"),
                set.getInt("parent_id"));
    }

    public void add(Genre genre) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO genre(genre_name, parent_id) values(?,?)");
        statement.setString(1, genre.getGenreName());
        if (genre.getParentId() == 0) statement.setNull(2, Types.INTEGER);
        else statement.setInt(2, genre.getParentId());
        statement.executeUpdate();
    }

    public void addAll(List<Genre> genres) throws SQLException {
        for (Genre genre : genres) {
            add(genre);
        }
    }

    public void remove(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM genre WHERE genre_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void update(Genre genre) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE genre SET genre_name = ?, parent_id = ? WHERE genre_id = ?");
        statement.setString(1, genre.getGenreName());
        if (genre.getParentId() == 0) statement.setNull(2, Types.INTEGER);
        else statement.setInt(2, genre.getParentId());
        statement.setInt(3, genre.getId());
        statement.executeUpdate();
    }

    public List<Genre> getByName(String name) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM genre WHERE UPPER(genre.genre_name) LIKE UPPER(?)");
        statement.setString(1, "%" + name + "%");
        ResultSet set = statement.executeQuery();
        List<Genre> genres = new ArrayList<>();
        while (set.next())
            genres.add(new Genre(set.getInt("genre_id"),
                    set.getString("genre_name"),
                    set.getInt("parent_id")));
        return genres;
    }

    public List<Genre> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        GenreFilter filter = (GenreFilter) entityFilter;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre " +
                "LEFT OUTER JOIN genre parent ON parent.genre_id = genre.parent_id WHERE " +
                "UPPER(genre.genre_name) LIKE UPPER(?) AND UPPER(parent.genre_name) LIKE UPPER(?)");
        statement.setString(1, "%" + filter.getName() + "%");
        statement.setString(2, "%" + filter.getParentName() + "%");
        ResultSet set = statement.executeQuery();
        List<Genre> genres = new ArrayList<>();
        while (set.next())
            genres.add(new Genre(set.getInt("genre_id"),
                    set.getString("genre_name"),
                    set.getInt("parent_id")));
        return genres;
    }
}
