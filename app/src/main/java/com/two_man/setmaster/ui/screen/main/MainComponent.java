package com.two_man.setmaster.ui.screen.main;

import com.two_man.setmaster.ui.base.activity.ContainerActivityComponent;
import com.two_man.setmaster.ui.base.fragment.FragmentModule;
import com.two_man.setmaster.ui.base.fragment.PerFragment;

import dagger.Component;

/**
 *
 */
@Component(dependencies = ContainerActivityComponent.class, modules = {FragmentModule.class})
@PerFragment
public interface MainComponent {
    void inject(MainFragmentView view);
}
