package music;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;

public interface View extends Observer{
    void createView();
    void displayAll();
    void displayGenres();
    void displayGenres(int numComparator) throws SQLException, ParseException;
    void update(Observable o, Object arg);
}
