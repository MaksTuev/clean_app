package com.agna.setmaster.ui.screen.main;

import com.agna.setmaster.ui.base.activity.ContainerActivityComponent;
import com.agna.setmaster.ui.base.fragment.FragmentModule;
import com.agna.setmaster.ui.base.fragment.PerFragment;

import dagger.Component;

/**
 *
 */
@Component(dependencies = ContainerActivityComponent.class, modules = {FragmentModule.class})
@PerFragment
public interface MainComponent {
    void inject(MainFragmentView view);
}
