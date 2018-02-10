package music.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import music.MusicModel;
import music.Model;

public class ModelModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Model.class).to(MusicModel.class);
    }
}