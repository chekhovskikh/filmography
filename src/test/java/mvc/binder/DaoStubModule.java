package mvc.binder;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import dao.*;
import entitiy.Film;
import entitiy.Franchise;
import entitiy.Producer;
import entitiy.Genre;
import mvc.dao.*;

import java.util.List;

public class DaoStubModule implements Module {
    protected List<Genre> genres;
    protected List<Producer> producers;
    protected List<Franchise> franchises;
    protected List<Film> films;

    public DaoStubModule(List<Genre> genres, List<Producer> producers, List<Franchise> franchises, List<Film> films) {
        this.genres = genres;
        this.producers = producers;
        this.franchises = franchises;
        this.films = films;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(EntityDao.class).annotatedWith(Names.named("genre")).toInstance(new GenreStubDao(genres));
        binder.bind(EntityDao.class).annotatedWith(Names.named("producer")).toInstance(new ProducerStubDao(producers));
        binder.bind(EntityDao.class).annotatedWith(Names.named("franchise")).toInstance(new FranchiseStubDao(franchises));
        binder.bind(EntityDao.class).annotatedWith(Names.named("film")).toInstance(new FilmStubDao(films));
    }
}
