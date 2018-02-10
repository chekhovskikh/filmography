package mvc.binder;

import com.google.inject.Guice;
import com.google.inject.Injector;
import entitiy.Film;
import entitiy.Franchise;
import entitiy.Producer;
import entitiy.Genre;
import util.binder.ControllerModule;
import util.binder.ModelModule;

import java.util.List;

public class InjectorStubBuilder {
    protected List<Genre> genres;
    protected List<Producer> producers;
    protected List<Franchise> franchises;
    protected List<Film> films;

    public InjectorStubBuilder(List<Genre> genres, List<Producer> producers, List<Franchise> franchises, List<Film> films) {
        this.genres = genres;
        this.producers = producers;
        this.franchises = franchises;
        this.films = films;
    }
    public Injector getInjector() {
        return Guice.createInjector(
                new DaoStubModule(genres, producers, franchises, films),
                new ModelModule(),
                new ControllerModule());
    }
}
