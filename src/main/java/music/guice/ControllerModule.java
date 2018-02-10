package music.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import music.Controller;
import music.MusicController;

public class ControllerModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Controller.class).to(MusicController.class);
    }
}
