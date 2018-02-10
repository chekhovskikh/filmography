package servlet.util;

import com.google.inject.Injector;
import mvc.Controller;
import mvc.Model;
import util.binder.InjectorBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataLayer {
    public static final String PATH_PROPERTIES_POSTGRES = "/postgres.properties";

    private static DataLayer instance = new DataLayer();

    private Model model = null;
    private Controller controller = null;


    public static DataLayer getInstance() {
        return instance;
    }

    private DataLayer() {
        try {
            InputStream inputProperties = getClass().getResourceAsStream(PATH_PROPERTIES_POSTGRES);
            Properties properties = new Properties();
            properties.load(inputProperties);

            Injector injector = new InjectorBuilder(properties).getInjector();
            controller = injector.getInstance(Controller.class);
            model = controller.getModel();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller getController(){
        return controller;
    }

    public Model getModel(){
        return model;
    }
}
