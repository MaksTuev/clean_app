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
package com.agna.setmaster.interactor.setting;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.setting.Setting;
import com.agna.setmaster.interactor.profile.event.ChangedStatus;
import com.agna.setmaster.interactor.profile.event.ProfileChangedEvent;
import com.agna.setmaster.interactor.setting.applyer.SettingApplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Responsible for applying settings and returning previous settings
 */
@PerApplication
public class SettingManager {
    private Map<Class<? extends Setting>, SettingApplier> settingAppliers;
    private List<Class<? extends Setting>> settingTypes;
    private PublishSubject<Profile> globalProfileChangedSubject = PublishSubject.create();

    private List<Profile> activeProfiles = new ArrayList<>();

    @Inject
    public SettingManager(Map<Class<? extends Setting>, SettingApplier> settingAppliers,
                          ArrayList<Class<? extends Setting>> settingTypes) {
        this.settingAppliers = settingAppliers;
        this.settingTypes = settingTypes;
    }

    public void onProfileChanged(ProfileChangedEvent event) {
        synchronized (this) {
            Profile newProfile = event.getProfile();
            Profile oldProfile = getProfile(newProfile.getId());
            if (oldProfile != null) {
                disableProfile(oldProfile);
            }
            if (newProfile.isActive() && event.getStatus() != ChangedStatus.DELETED) {
                enableProfile(newProfile);
            }
        }
    }

    public Observable<Profile> observeGlogalProfileChanged() {
        return globalProfileChangedSubject;
    }

    private void enableProfile(Profile newProfile) {
        addProfileToActiveProfiles(newProfile);
        for (Class<? extends Setting> settingType : settingTypes) {
            List<Profile> profilesWithSetting = StreamSupport.stream(activeProfiles)
                    .filter(profile -> (profile.getSetting(settingType) != null))
                    .collect(Collectors.toList());
            if (profilesWithSetting.size() == 0) {
                continue;
            }
            Profile appliedProfilesWithSetting = profilesWithSetting.get(0);
            if (appliedProfilesWithSetting.getId().equals(newProfile.getId())) {
                SettingApplier settingApplier = settingAppliers.get(settingType);
                if (profilesWithSetting.size() == 1 && !appliedProfilesWithSetting.isGlobal()) {
                    saveOldSettingInGlobalProfile(settingType);
                }
                Setting newSetting = newProfile.getSetting(settingType);
                settingApplier.apply(newSetting);
            }
        }
    }

    private void disableProfile(Profile oldProfile) {
        for (Class<? extends Setting> settingType : settingTypes) {
            List<Profile> profilesWithSetting = StreamSupport.stream(activeProfiles)
                    .filter(profile -> (profile.getSetting(settingType) != null))
                    .collect(Collectors.toList());
            if (profilesWithSetting.size() == 0) {
                continue;
            }
            Profile appliedProfilesWithSetting = profilesWithSetting.get(0);
            if (appliedProfilesWithSetting.getId().equals(oldProfile.getId())) {
                if (profilesWithSetting.size() > 1) {
                    Profile newAppliedProfilesWithSetting = profilesWithSetting.get(1);
                    Setting newSetting = newAppliedProfilesWithSetting.getSetting(settingType);
                    settingAppliers.get(settingType).apply(newSetting);
                    if (newAppliedProfilesWithSetting.isGlobal()) {
                        clearSettingInGlobalProfile(newAppliedProfilesWithSetting, settingType);
                    }
                }
            }
        }
        activeProfiles.remove(oldProfile);
    }

    private void clearSettingInGlobalProfile(Profile globalProfile, Class<? extends Setting> settingType) {
        Setting setting = globalProfile.getSetting(settingType);
        globalProfile.deleteSetting(setting);
        globalProfileChangedSubject.onNext(globalProfile);
    }

    private void saveOldSettingInGlobalProfile(Class<? extends Setting> settingType) {
        Profile globalProfile = getGlobalProfile();
        if (globalProfile != null) {
            Setting currentSetting = settingAppliers.get(settingType).getCurrent();
            globalProfile.addSetting(currentSetting);
            globalProfileChangedSubject.onNext(globalProfile);
        }
    }

    private Profile getGlobalProfile() {
        for (Profile profile : activeProfiles) {
            if (profile.isGlobal()) {
                return profile;
            }
        }
        return null;
    }

    private void addProfileToActiveProfiles(Profile newProfile) {
        boolean profileAdded = false;
        for (int i = 0; i < activeProfiles.size(); i++) {
            if (activeProfiles.get(i).getPriority() <= newProfile.getPriority()) {
                activeProfiles.add(i, newProfile);
                profileAdded = true;
                break;
            }
        }
        if (!profileAdded) {
            activeProfiles.add(0, newProfile);
        }
    }

    private Profile getProfile(String id) {
        return StreamSupport.stream(activeProfiles)
                .filter(profile -> profile.getId().equals(id))
                .reduce(null, (prev, next) -> next);
    }
}
