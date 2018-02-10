package dao;

import entitiy.Entity;
import entitiy.filter.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;


public interface EntityDao<T extends Entity> {
    void closeConnection() throws SQLException;
    List<T> getAll() throws SQLException, ParseException;
    T get(int id) throws SQLException, ParseException;
    List<T> getByName(String name) throws SQLException, ParseException;
    List<T> getByFilter(EntityFilter filter) throws SQLException, ParseException;
    void add(T entity) throws SQLException;
    void addAll(List<T> entities) throws SQLException;
    void remove(int id) throws SQLException;
    void update(T entity) throws SQLException;

}
