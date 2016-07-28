package com.agna.setmaster.module.initialize;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.module.profile.ProfileService;
import com.agna.setmaster.module.setting.SettingManager;

import javax.inject.Inject;

import rx.Observable;

/**
 *
 */
@PerApplication
public class InitializeAppInteractor {

    private final SettingManager settingManager;
    private final ProfileService profileService;
    private boolean initialized = false;

    @Inject
    public InitializeAppInteractor(SettingManager settingManager,
                                   ProfileService profileService) {
        this.settingManager = settingManager;
        this.profileService = profileService;
    }

    public Observable<Void> initialize() {
        if(initialized){
            return Observable.just(null);

        } else {
            initialized = true;
            return profileService.initialize();
        }
    }
}
