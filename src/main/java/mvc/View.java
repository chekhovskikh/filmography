package mvc;

import java.util.Observable;
import java.util.Observer;

public interface View extends Observer{
    void createView();
    void displayAll();
    void update(Observable o, Object arg);
}
