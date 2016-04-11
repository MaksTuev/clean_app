package com.two_man.setmaster.interactor;

import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.SettingManager;

import javax.inject.Inject;

import rx.Observable;

/**
 *
 */
public class InitializeAppInteractor {

    private final SettingManager settingManager;
    private final ProfileService profileService;

    @Inject
    public InitializeAppInteractor(SettingManager settingManager, ProfileService profileService) {
        this.settingManager = settingManager;
        this.profileService = profileService;
    }

    public Observable<Object> initialize() {
        return Observable.just(null);
    }
}
