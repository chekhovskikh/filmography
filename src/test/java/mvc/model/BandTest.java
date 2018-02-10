package mvc.model;

import entities.Band;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class BandTest extends ModelTest {

    @Test
    public void testAddBand() throws SQLException, IOException {
        Date date = new Date();
        Band band = new Band( 1,"RHCP", date);
        Band band1 = new Band(2,"Hypocrisy",date );
        model.addBand(band);
        model.addBand(band1);
        assertEquals(band, bands.get(0));
        //model.close();
    }

    @Test
    public void testRemoveBand() throws SQLException, IOException {
        Date date = new Date();
        Band band = new Band( 1,"RHCP", date);
        model.addBand(band);
        assertEquals(band, bands.get(0));
        model.removeBand(0);
        assertEquals(0, bands.size());
    }

    @Test
    public void testGetBand() throws SQLException, IOException, ParseException {
        Date date = new Date();
        Band band = new Band( 1,"RHCP", date);
        Band band1 = new Band(2,"Hypocrisy",date );
        model.addBand(band);
        model.addBand(band1);
        Band band2 = model.getBand(1);
        assertEquals(band2, band1);
    }
}
