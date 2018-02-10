package mvc.model;

import entitiy.Producer;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class ProducerTest extends ModelTest {

    @Test
    public void testAddProducer() throws SQLException, IOException {
        Date date = new Date();
        Producer producerOne = new Producer( "Кемерон","США", date);
        Producer producerTwo = new Producer("Михалков","Россия", date);
        model.addProducer(producerOne);
        model.addProducer(producerTwo);
        assertEquals(producerOne, producers.get(0));
        //model.close();
    }

    @Test
    public void testRemoveProducer() throws SQLException, IOException {
        Date date = new Date();
        Producer producer = new Producer( "Кемерон","США", date);
        model.addProducer(producer);
        assertEquals(producer, producers.get(0));
        model.removeProducer(0);
        assertEquals(0, producers.size());
    }

    @Test
    public void testGetBand() throws SQLException, IOException, ParseException {
        Date date = new Date();
        Producer producerOne = new Producer( "Кемерон","США", date);
        Producer producerTwo = new Producer("Михалков","Россия", date);
        model.addProducer(producerOne);
        model.addProducer(producerTwo);
        Producer producer2 = model.getProducer(1);
        assertEquals(producer2, producerTwo);
    }
}
