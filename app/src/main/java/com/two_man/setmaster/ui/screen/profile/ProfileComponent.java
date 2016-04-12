package com.two_man.setmaster.ui.screen.profile;

import com.two_man.setmaster.ui.app.AppComponent;
import com.two_man.setmaster.ui.base.activity.ActivityModule;
import com.two_man.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ProfileModule.class})
@PerActivity
public interface ProfileComponent {
    void inject(ProfileActivity activity);
}
