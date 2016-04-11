package com.two_man.setmaster.interactor;

import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.SettingManager;
import com.two_man.setmaster.ui.app.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @PerApplication
    @Provides
    InitializeAppInteractor provideInitializeAppInteractor(SettingManager settingManager, ProfileService profileService){
        return new InitializeAppInteractor(settingManager, profileService);
    }
}
