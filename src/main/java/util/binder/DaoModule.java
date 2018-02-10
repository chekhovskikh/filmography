package util.binder;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import dao.*;

import java.sql.SQLException;
import java.util.Properties;

public class DaoModule implements Module {

    private Properties properties;

    public DaoModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void configure(Binder binder) {
        try {
            binder.bind(EntityDao.class).annotatedWith(Names.named("genre")).toInstance(new GenreDao(properties));
            binder.bind(EntityDao.class).annotatedWith(Names.named("producer")).toInstance(new ProducerDao(properties));
            binder.bind(EntityDao.class).annotatedWith(Names.named("franchise")).toInstance(new FranchiseDao(properties));
            binder.bind(EntityDao.class).annotatedWith(Names.named("film")).toInstance(new FilmDao(properties));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
