package music.guice;

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
            binder.bind(EntityDao.class).annotatedWith(Names.named("band")).toInstance(new BandDao(properties));
            binder.bind(EntityDao.class).annotatedWith(Names.named("album")).toInstance(new AlbumDao(properties));
            binder.bind(EntityDao.class).annotatedWith(Names.named("song")).toInstance(new SongDao(properties));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
