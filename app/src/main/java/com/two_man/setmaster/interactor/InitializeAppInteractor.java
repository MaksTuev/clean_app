package com.two_man.setmaster.interactor;

import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.SettingManager;

import rx.Observable;

/**
 *
 */
public class InitializeAppInteractor {

    private final SettingManager settingManager;
    private final ProfileService profileService;
    private boolean initialized = false;

    public InitializeAppInteractor(SettingManager settingManager, ProfileService profileService) {
        this.settingManager = settingManager;
        this.profileService = profileService;
    }

    public Observable<Void> initialize() {
        return initialized
                ? Observable.just(null)
                : profileService.initialize()
                .doOnNext(o -> initialized = true);
    }
}
