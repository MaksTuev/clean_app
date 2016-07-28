package com.agna.setmaster.ui.screen.profile;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ProfileModule.class})
@PerActivity
public interface ProfileComponent {
    void inject(ProfileActivity activity);
}
