package dao;

import entitiy.Film;
import util.dbdrivers.DbManager;
import entitiy.filter.EntityFilter;
import entitiy.filter.FilmFilter;
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

public class FilmDao implements EntityDao<Film> {
    private Connection connection;
    private Statement statement;
    private List<Film> films;

    public FilmDao(Properties properties) throws SQLException {
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String sid = properties.getProperty("sid");

        DbManager manager = new PostgresManager(username, password);
        connection = manager.connect(host, port, sid);
        statement = connection.createStatement();
        films = new ArrayList<>();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public List<Film> getAll() throws SQLException, ParseException {
        films.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM film");
        while (set.next())
            films.add(new Film(set.getInt("film_id"),
                    set.getString("film_name"),
                    EntityUtils.parseFullDate(set.getString("duration")),
                    set.getInt("producer_id"),
                    set.getInt("franchise_id"),
                    set.getInt("genre_id")));
        return films;
    }

    public Film get(int id) throws SQLException, ParseException {
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM film WHERE id=" + id);
        set.next();
        return new Film(set.getInt("film_id"),
                set.getString("film_name"),
                EntityUtils.parseFullDate(set.getString("duration")),
                set.getInt("producer_id"),
                set.getInt("franchise_id"),
                set.getInt("genre_id"));
    }

    public void add(Film film) throws SQLException {
        statement.executeUpdate("INSERT INTO film(film_name,duration,genre_id,producer_id,franchise_id) values('" + film.getFilmName() +
                "',to_date('" + film.timeToString() + "', 'MI:SS')," +
                (film.getGenreId() == 0 ? "null" : film.getGenreId()) + "," +
                film.getProducerId()+ "," +
                (film.getFranchiseId() == 0 ? "null" : film.getFranchiseId()));
    }

    public void addAll(List<Film> films) throws SQLException {
        String sqlRequest = "INSERT INTO film(film_name,duration,genre_id,producer_id,franchise_id) values ";
        for (int i = 0, size = films.size(); i < size; i++) {
            Film film = films.get(i);
            sqlRequest += "('" + film.getFilmName() +
                    "',to_date('" + film.timeToString() + "', 'MI:SS')," +
                    (film.getGenreId() == 0 ? "null" : film.getGenreId()) + "," +
                    film.getProducerId()+ "," +
                    (film.getFranchiseId() == 0 ? "null" : film.getFranchiseId());
            if (i != size - 1)
                sqlRequest += ",";
        }
        statement.executeUpdate(sqlRequest);
    }

    public void remove(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM film WHERE film_id=" + id);
    }

    public void update(Film film) throws SQLException {
        statement.executeUpdate("UPDATE film " +
                "SET film_name='" + film.getFilmName() +
                "', duration=to_date('" + film.timeToString() + "', 'MI:SS')" +
                ", genre_id=" + (film.getGenreId() == 0 ? "null" : film.getGenreId()) +
                ", producer_id=" + film.getProducerId() +
                ", franchise_id=" + (film.getFranchiseId() == 0 ? "null" : film.getFranchiseId()) +
                " WHERE film_id=" + film.getGenreId());
    }

    public List<Film> getByName(String name) throws SQLException, ParseException {
        films.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM film WHERE " + regexpLike("film_name", name));
        while (set.next())
            films.add(new Film(set.getInt("film_id"),
                    set.getString("film_name"),
                    EntityUtils.parseFullDate(set.getString("duration")),
                    set.getInt("producer_id"),
                    set.getInt("franchise_id"),
                    set.getInt("genre_id")));
        return films;
    }

    public List<Film> getByFilter(EntityFilter entityFilter) throws SQLException, ParseException {
        FilmFilter filter = (FilmFilter) entityFilter;
        films.clear();
        ResultSet set;
        set = statement.executeQuery("SELECT * FROM film " +
                "LEFT OUTER JOIN producer ON film.producer_id = producer.producer_id " +
                "LEFT OUTER JOIN genre ON film.genre_id = genre.genre_id " +
                "LEFT OUTER JOIN franchise ON film.franchise_id = franchise.franchise_id WHERE " +
                (filter.timeToString().equals("00:00") ? "" : "TO_CHAR(film.duration,'MI:SS') = '" + filter.timeToString() + "' AND ") +
                regexpLike("film.film_name", filter.getGenreName()) + " AND " +
                regexpLike("producer.producer_name", filter.getProducerName()) + " AND " +
                regexpLike("genre.genre_name", filter.getGenreName()) + " AND " +
                regexpLike("franchise.franchise_name", filter.getFranchiseName()));
        while (set.next())
            films.add(new Film(set.getInt("film_id"),
                    set.getString("film_name"),
                    EntityUtils.parseFullDate(set.getString("duration")),
                    set.getInt("producer_id"),
                    set.getInt("franchise_id"),
                    set.getInt("genre_id")));
        return films;
    }

    private String regexpLike(String attribute, String value){
        return "REGEXP_LIKE(" + attribute + ", '(^| |-)+" + value + "\\w*','i')";
    }
}
