package util.binder;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Properties;

public class InjectorBuilder {

    private Properties properties;

    public InjectorBuilder(Properties properties) {
        this.properties = properties;
    }

    public Injector getInjector() {
        return Guice.createInjector(
                new DaoModule(properties),
                new ModelModule(),
                new ControllerModule());
    }
}
