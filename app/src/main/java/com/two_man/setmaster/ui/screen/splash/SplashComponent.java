package com.two_man.setmaster.ui.screen.splash;

import dagger.Component;
import ru.litres.android.audio.ui.base.activity.ContainerActivityComponent;
import ru.litres.android.audio.ui.base.fragment.FragmentModule;
import ru.litres.android.audio.ui.base.fragment.PerFragment;

@PerFragment
@Component(dependencies = ContainerActivityComponent.class, modules = FragmentModule.class)
public interface SplashComponent {
    void inject(SplashFragment fragment);
}
