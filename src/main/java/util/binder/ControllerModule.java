package util.binder;

import com.google.inject.Binder;
import com.google.inject.Module;
import mvc.Controller;
import mvc.FilmController;

public class ControllerModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Controller.class).to(FilmController.class);
    }
}
