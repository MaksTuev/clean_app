package com.agna.setmaster.ui.screen.editprofile;

import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.ui.base.PerScreen;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class EditProfileScreenModule {
    private Profile profile;

    public EditProfileScreenModule(Profile profile) {
        this.profile = profile;
    }

    @Provides
    @PerScreen
    public ProfileHolder provideProfile() {
        return new ProfileHolder(profile);
    }
}
