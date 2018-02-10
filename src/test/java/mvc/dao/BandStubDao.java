package mvc.dao;

import dao.EntityDao;
import entities.Band;
import entities.filters.EntityFilter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BandStubDao implements EntityDao<Band> {
    private List<Band> bands;

    public BandStubDao() {
        bands = new ArrayList<>();
    }

    public BandStubDao(List<Band> bands) {
        this.bands = bands;
    }


    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Band> getAll() throws SQLException, ParseException {
        return bands;
    }

    public Band get(int id) throws SQLException, ParseException {
        return bands.get(id);
    }

    public List<Band> getByName(String name) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }

    public void add(Band band) throws SQLException {
        bands.add(band);
    }

    public void addAll(List<Band> entities) throws SQLException {
        bands.addAll(entities);
    }

    public void remove(int id) throws SQLException {
        bands.remove(id);
    }

    public void update(Band entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Band> getByFilter(EntityFilter filter) throws SQLException, ParseException {
        throw new UnsupportedOperationException();
    }
}
