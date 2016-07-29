package com.agna.setmaster.ui.screen.editprofile;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.screen.profile.ProfileModule;

import dagger.Component;

/**
 *
 */
@PerScreen
@Component(dependencies = AppComponent.class, modules = {
        ActivityModule.class,
        ProfileModule.class})
public interface EditProfileComponent {
    void inject(EditProfileActivity view);
}
