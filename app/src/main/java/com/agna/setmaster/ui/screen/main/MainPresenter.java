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
package com.agna.setmaster.ui.screen.main;

import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.interactor.profile.ProfileService;
import com.agna.setmaster.app.scheduler.SchedulersProvider;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.common.navigation.Navigator;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 *
 */
@PerScreen
public class MainPresenter extends BasePresenter<MainFragmentView> {

    private ProfileService profileService;
    private Navigator navigator;
    private SchedulersProvider schedulersProvider;
    private ArrayList<Profile> profiles = new ArrayList<>();

    @Inject
    public MainPresenter(ProfileService profileService,
                         Navigator navigator,
                         SchedulersProvider schedulersProvider) {
        this.profileService = profileService;
        this.navigator = navigator;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        showData();
        observeChanges();
    }

    private void observeChanges() {
        profileService.observeProfileChanged()
                .observeOn(schedulersProvider.main())
                .subscribe(event -> showData());
    }

    private void showData() {
        profiles = profileService.getAllProfiles();
        getView().showProfiles(profiles);
    }

    public void createNewProfile() {
        navigator.openNewProfile();
    }

    public void onStart() {
        showData();
    }

    public void openProfile(Profile profile) {
        navigator.openProfile(profile);
    }
}
