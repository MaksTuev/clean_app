package com.two_man.setmaster.ui.app;

import dagger.Module;
import ru.litres.android.audio.interactor.InteractorModule;
import ru.litres.android.audio.module.player.PlayerModule;
import ru.litres.android.audio.module.repository.RepositoryModule;

@Module(includes = {
        PlayerModule.class,
        RepositoryModule.class,
        InteractorModule.class})
public class AppModule {

}
