package com.two_man.setmaster.ui.screen.profile;

import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.activity.PerActivity;
import com.two_man.setmaster.ui.navigation.Navigator;

import javax.inject.Inject;

/**
 *
 */
@PerActivity
public class ProfilePresenter extends BasePresenter<ProfileActivity> {

    private ProfileService profileService;
    private Navigator navigator;
    private Profile profile;

    @Inject
    public ProfilePresenter(ProfileService profileService, Navigator navigator) {
        this.profileService = profileService;
        this.navigator = navigator;
    }

    public void init(Profile profile) {
        this.profile = profile;
    }
}
