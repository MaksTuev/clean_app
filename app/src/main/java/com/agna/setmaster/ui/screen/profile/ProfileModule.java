package com.agna.setmaster.ui.screen.profile;

import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.ui.base.PerScreen;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ProfileModule {
    private Profile profile;

    public ProfileModule(Profile profile) {
        this.profile = profile;
    }

    @Provides
    @PerScreen
    public Profile provideProfile() {
        return profile;
    }
}
