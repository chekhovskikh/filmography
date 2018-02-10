package mvc.dao;

import dao.EntityDao;
import entitiy.Franchise;
import entitiy.filter.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FranchiseStubDao implements EntityDao<Franchise> {
    private List<Franchise> franchises;

    public FranchiseStubDao() {
        franchises = new ArrayList<>();
    }

    public FranchiseStubDao(List<Franchise> franchises) {
        this.franchises = franchises;
    }


    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Franchise> getAll() throws SQLException, ParseException {
        return franchises;
    }

    public Franchise get(int id) throws SQLException, ParseException {
        return franchises.get(id);
    }

    public List<Franchise> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Franchise franchise) throws SQLException {
        franchises.add(franchise);
    }

    public void addAll(List<Franchise> entities) throws SQLException {
        franchises.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        franchises.remove(id);
    }

    public void update(Franchise entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Franchise> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
