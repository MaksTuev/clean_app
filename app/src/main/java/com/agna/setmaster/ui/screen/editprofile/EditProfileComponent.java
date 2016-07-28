package com.agna.setmaster.ui.screen.editprofile;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.activity.ActivityModule;

import dagger.Component;

/**
 *
 */
@PerScreen
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, EditProfileModule.class})
public interface EditProfileComponent {
    void inject(EditProfileActivity view);
}
