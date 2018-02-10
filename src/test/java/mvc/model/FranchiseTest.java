package mvc.model;

import entitiy.Franchise;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class FranchiseTest extends ModelTest {

    @Test
    public void testAddFranchise() throws SQLException, IOException {
        Date date = new Date();
        Franchise franchiseOne = new Franchise("Форсаж", "США", date);
        Franchise franchiseTwo = new Franchise("Звездные Войны", "США", date);
        model.addFranchise(franchiseOne);
        model.addFranchise(franchiseTwo);
        assertEquals(franchiseOne, franchises.get(0));
    }

    @Test
    public void testRemoveFranchise() throws SQLException, IOException {
        Date date = new Date();
        Franchise franchiseOne = new Franchise("Форсаж", "США", date);
        Franchise franchiseTwo = new Franchise("Звездные Войны", "США", date);
        model.addFranchise(franchiseOne);
        assertEquals(franchiseOne, franchises.get(0));
        model.addFranchise(franchiseTwo);
        assertEquals(franchiseTwo, franchises.get(1));
        model.removeFranchise(0);
        assertEquals(1, franchises.size());
    }

    @Test
    public void testGetAlbum() throws SQLException, IOException, ParseException {
        Date date = new Date();
        Franchise franchiseOne = new Franchise("Форсаж", "США", date);
        Franchise franchiseTwo = new Franchise("Звездные Войны", "США", date);
        model.addFranchise(franchiseOne);
        model.addFranchise(franchiseTwo);
        Franchise franchise2 = model.getFranchise(1);
        assertEquals(franchise2, franchiseTwo);
    }
}
