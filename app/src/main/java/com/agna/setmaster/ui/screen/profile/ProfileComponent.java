package com.agna.setmaster.ui.screen.profile;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.dialog.module.ActivityDialogModule;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {
        ActivityModule.class,
        ProfileModule.class,
        ActivityDialogModule.class})
@PerScreen
public interface ProfileComponent {
    void inject(ProfileActivity activity);
}
