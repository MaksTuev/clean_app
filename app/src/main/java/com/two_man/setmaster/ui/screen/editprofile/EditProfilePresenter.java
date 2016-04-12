package com.two_man.setmaster.ui.screen.editprofile;

import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.navigation.Navigator;

import javax.inject.Inject;

/**
 *
 */
public class EditProfilePresenter extends BasePresenter<EditProfileActivity> {

    private final Navigator navigator;
    private final ProfileService profileService;

    private Profile profile;

    @Inject
    public EditProfilePresenter(ProfileService profileService, Navigator navigator) {
        this.profileService = profileService;
        this.navigator = navigator;
    }

    public void init(Profile profile){
        this.profile = profile;
    }

    public void saveProfile(String name, int iconRes) {
        if(profile == null){
            profile = new Profile(name, iconRes);
            profileService.addProfile(profile);
        } else {
            profile.setName(name);
            profile.setImageResId(iconRes);
            profileService.updateProfile(profile);
        }
        getView().goBack();
        navigator.openProfile(profileService.getProfile(profile.getId()));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        getView().bindProfile(profile);
    }
}
