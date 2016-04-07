package com.two_man.setmaster.ui.base.activity;

import dagger.Component;
import ru.litres.android.audio.ui.app.AppComponent;
import ru.litres.android.audio.ui.base.fragment.BaseFragmentView;


/**
 * компонет activity, используемый в качестве {@link Component#dependencies()} в компонентах экранов,
 * у которых view наследуется от {@link BaseFragmentView}
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ContainerActivityComponent {


}
