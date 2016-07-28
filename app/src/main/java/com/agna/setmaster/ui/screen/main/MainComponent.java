package com.agna.setmaster.ui.screen.main;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.fragment.FragmentModule;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class})
@PerScreen
public interface MainComponent {
    void inject(MainFragmentView view);
}
