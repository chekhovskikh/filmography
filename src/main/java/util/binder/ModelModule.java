package util.binder;

import com.google.inject.Binder;
import com.google.inject.Module;
import mvc.FilmModel;
import mvc.Model;

public class ModelModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Model.class).to(FilmModel.class);
    }
}