package dao;

import entitiy.Film;
import util.EntityUtil;
import util.dbdriver.DbManager;
import entitiy.filter.EntityFilter;
import entitiy.filter.FilmFilter;
import util.dbdriver.PostgresManager;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FilmDao implements EntityDao<Film> {
    private Connection connection;

    public FilmDao(Properties properties) throws SQLException {
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

    public List<Film> getAll() throws SQLException, ParseException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM film");
        List<Film> films = new ArrayList<>();
        while (set.next())
            films.add(new Film(set.getInt("film_id"),
                    set.getString("film_name"),
                    EntityUtil.parseTime(set.getString("duration")),
                    set.getInt("producer_id"),
                    set.getInt("franchise_id"),
                    set.getInt("genre_id")));
        return films;
    }

    public Film get(int id) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film_id = ?");
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        set.next();
        return new Film(set.getInt("film_id"),
                set.getString("film_name"),
                EntityUtil.parseTime(set.getString("duration")),
                set.getInt("producer_id"),
                set.getInt("franchise_id"),
                set.getInt("genre_id"));
    }

    public void add(Film film) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO film(film_name, " +
                "duration, genre_id, producer_id, franchise_id) values(?, to_timestamp(?, 'HH24:MI:SS'), ?, ?, ?)");
        statement.setString(1, film.getFilmName());
        statement.setString(2, film.timeToString());
        if (film.getGenreId() == 0) statement.setNull(3, Types.INTEGER);
        else statement.setInt(3, film.getGenreId());
        statement.setInt(4, film.getProducerId());
        if (film.getFranchiseId() == 0) statement.setNull(5, Types.INTEGER);
        else statement.setInt(5, film.getFranchiseId());
        statement.executeUpdate();
    }

    public void addAll(List<Film> films) throws SQLException {
        for (Film film : films) {
            add(film);
        }
    }

    public void remove(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM film WHERE film_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void update(Film film) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE film SET film_name = ?, " +
                "duration = to_timestamp(?, 'HH24:MI:SS'), genre_id = ?, producer_id = ?, " +
                "franchise_id = ?  WHERE film_id = ?");
        statement.setString(1, film.getFilmName());
        statement.setString(2, film.timeToString());
        if (film.getGenreId() == 0) statement.setNull(3, Types.INTEGER);
        else statement.setInt(3, film.getGenreId());
        statement.setInt(4, film.getProducerId());
        if (film.getFranchiseId() == 0) statement.setNull(5, Types.INTEGER);
        else statement.setInt(5, film.getFranchiseId());
        statement.setInt(6, film.getId());
        statement.executeUpdate();
    }

    public List<Film> getByName(String name) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM film WHERE UPPER(film_name) LIKE UPPER(?)");
        statement.setString(1, "%" + name + "%");
        ResultSet set = statement.executeQuery();
        List<Film> films = new ArrayList<>();
        while (set.next())
            films.add(new Film(set.getInt("film_id"),
                    set.getString("film_name"),
                    EntityUtil.parseTime(set.getString("duration")),
                    set.getInt("producer_id"),
                    set.getInt("franchise_id"),
                    set.getInt("genre_id")));
        return films;
    }

    public List<Film> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        FilmFilter filter = (FilmFilter) entityFilter;
        String endQuery = "";
        Boolean isFilteringTime = !filter.timeToString().equals("11:00:00");
        if (isFilteringTime)
            endQuery = "AND TO_CHAR(film.duration,'HH24:MI:SS') = ?";

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film " +
                "LEFT OUTER JOIN producer ON film.producer_id = producer.producer_id " +
                "LEFT OUTER JOIN genre ON film.genre_id = genre.genre_id " +
                "LEFT OUTER JOIN franchise ON film.franchise_id = franchise.franchise_id WHERE " +
                "UPPER(film.film_name) LIKE UPPER(?) AND UPPER(producer.producer_name) LIKE UPPER(?) AND " +
                "UPPER(genre.genre_name) LIKE UPPER(?) AND UPPER(franchise.franchise_name) LIKE UPPER(?) " + endQuery);
        statement.setString(1, "%" + filter.getName() + "%");
        statement.setString(2, "%" + filter.getProducerName() + "%");
        statement.setString(3, "%" + filter.getGenreName() + "%");
        statement.setString(4, "%" + filter.getFranchiseName() + "%");
        if (isFilteringTime) {
            //noinspection JpaQueryApiInspection
            statement.setString(5, filter.timeToString());
        }
        ResultSet set = statement.executeQuery();
        List<Film> films = new ArrayList<>();
        while (set.next())
            films.add(new Film(set.getInt("film_id"),
                    set.getString("film_name"),
                    EntityUtil.parseTime(set.getString("duration")),
                    set.getInt("producer_id"),
                    set.getInt("franchise_id"),
                    set.getInt("genre_id")));
        return films;
    }
}
