/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.ui.screen.editprofile;

import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.module.profile.ProfileService;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.common.navigation.Navigator;

import javax.inject.Inject;

/**
 *
 */
@PerScreen
public class EditProfilePresenter extends BasePresenter<EditProfileActivity> {

    private final Navigator navigator;
    private final ProfileService profileService;

    private Profile profile;

    @Inject
    public EditProfilePresenter(ProfileService profileService, Navigator navigator, ProfileHolder profileHolder) {
        this.profileService = profileService;
        this.navigator = navigator;
        this.profile = profileHolder.getProfile();
    }

    public void saveProfile(String name, int iconRes) {
        if (profile == null) {
            profile = new Profile(name, iconRes);
            profileService.addProfile(profile);
        } else {
            profile.setName(name);
            profile.setIconId(iconRes);
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
