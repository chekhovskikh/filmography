package mvc.dao;

import dao.EntityDao;
import entitiy.Producer;
import entitiy.filter.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProducerStubDao implements EntityDao<Producer> {
    private List<Producer> producers;

    public ProducerStubDao() {
        producers = new ArrayList<>();
    }

    public ProducerStubDao(List<Producer> producers) {
        this.producers = producers;
    }


    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Producer> getAll() throws SQLException, ParseException {
        return producers;
    }

    public Producer get(int id) throws SQLException, ParseException {
        return producers.get(id);
    }

    public List<Producer> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Producer producer) throws SQLException {
        producers.add(producer);
    }

    public void addAll(List<Producer> entities) throws SQLException {
        producers.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        producers.remove(id);
    }

    public void update(Producer entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Producer> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
