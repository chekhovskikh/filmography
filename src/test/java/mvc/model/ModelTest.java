package mvc.model;

import com.google.inject.Injector;
import entitiy.Franchise;
import entitiy.Producer;
import entitiy.Genre;
import entitiy.Film;
import mvc.Model;
import mvc.binder.InjectorStubBuilder;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelTest {
    protected List<Genre> genres;
    protected List<Producer> producers;
    protected List<Franchise> franchises;
    protected List<Film> films;
    protected Model model;

    @Before
    public void init() {
        genres = new ArrayList<>();
        producers = new ArrayList<>();
        franchises = new ArrayList<>();
        films = new ArrayList<>();

        Injector injector = new InjectorStubBuilder(genres, producers, franchises, films).getInjector();
        model = injector.getInstance(Model.class);
    }
}
