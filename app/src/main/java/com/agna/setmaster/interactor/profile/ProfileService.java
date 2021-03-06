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
package com.agna.setmaster.interactor.profile;

import android.support.annotation.Nullable;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.domain.ConditionSet;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.interactor.condition.ComplexConditionChecker;
import com.agna.setmaster.interactor.condition.simple.ConditionStateChangedEvent;
import com.agna.setmaster.interactor.profile.event.ChangedStatus;
import com.agna.setmaster.interactor.profile.event.ProfileChangedEvent;
import com.agna.setmaster.interactor.setting.SettingManager;
import com.agna.setmaster.interactor.profile.storage.ProfileStorage;
import com.agna.setmaster.util.CloneUtil;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import java8.util.stream.StreamSupport;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * manage profiles
 */
@PerApplication
public class ProfileService {

    private ComplexConditionChecker complexConditionChecker;
    private ProfileStorage profileStorage;
    private DefaultProfileCreator defaultProfileCreator;
    private SettingManager settingManager;

    private ArrayList<Profile> profiles = new ArrayList<>();

    private PublishSubject<ProfileChangedEvent> profileChangedSubject = PublishSubject.create();

    @Inject
    public ProfileService(ComplexConditionChecker complexConditionChecker,
                          ProfileStorage profileStorage,
                          DefaultProfileCreator defaultProfileCreator,
                          SettingManager settingManager) {
        this.complexConditionChecker = complexConditionChecker;
        this.profileStorage = profileStorage;
        this.defaultProfileCreator = defaultProfileCreator;
        this.settingManager = settingManager;
        initListeners();
    }

    public Observable<Void> initialize() {
        return profileStorage.getAllProfiles()
                .map(profiles -> initGlobalProfile(profiles))
                .flatMap(Observable::from)
                .doOnNext(this::initProfile)
                .toList()
                .map(list -> null);
    }

    public Observable<ProfileChangedEvent> observeProfileChanged() {
        return profileChangedSubject;
    }

    public void updateProfile(Profile profile) {
        synchronized (this) {
            Profile newProfile = profile.clone();
            Profile oldProfile = getProfileOrigin(newProfile.getId());
            profiles.remove(oldProfile);
            profiles.add(newProfile);
            profileStorage.update(newProfile);
            updateConditionRegistrations(oldProfile, newProfile);
            notifyOnProfileChangedListeners(newProfile, ChangedStatus.UPDATED);
        }
    }

    public void deleteProfile(String id) {
        synchronized (this) {
            Profile profile = getProfileOrigin(id);
            profiles.remove(profile);
            profileStorage.remove(profile);
            updateConditionRegistrations(profile, null);
            notifyOnProfileChangedListeners(profile, ChangedStatus.DELETED);
        }
    }

    public void addProfile(Profile profile) {
        synchronized (this) {
            Profile newProfile = profile.clone();
            profiles.add(newProfile);
            profileStorage.add(newProfile);
            updateConditionRegistrations(null, newProfile);
            notifyOnProfileChangedListeners(newProfile, ChangedStatus.UPDATED);
        }
    }

    public Profile getProfile(String id) {
        synchronized (this) {
            return getProfileOrigin(id).clone();
        }
    }

    public ArrayList<Profile> getAllProfiles() {
        synchronized (this) {
            ArrayList<Profile> profiles = CloneUtil.cloneProfiles(this.profiles);
            for (Profile profile : profiles) {
                if (profile.isGlobal()) {
                    profiles.remove(profile);
                    break;
                }
            }
            Collections.sort(profiles);
            return profiles;
        }
    }

    public void onGlobalProfileChanged(Profile globalProfile) {
        synchronized (this) {
            Profile newProfile = globalProfile.clone();
            Profile oldProfile = getProfileOrigin(newProfile.getId());
            profiles.remove(oldProfile);
            profiles.add(newProfile);
            profileStorage.update(newProfile);
        }
    }

    private ArrayList<Profile> initGlobalProfile(ArrayList<Profile> profiles) {
        Profile globalProfile = null;
        for (Profile profile : profiles) {
            if (profile.isGlobal()) {
                globalProfile = profile;
                profiles.remove(profile);
                break;
            }
        }
        if (globalProfile == null) {
            globalProfile = defaultProfileCreator.createGlobal();
            profileStorage.add(globalProfile);
        }
        globalProfile.setActive(true);
        this.profiles.add(globalProfile);
        notifyOnProfileChangedListeners(globalProfile, ChangedStatus.UPDATED);
        return profiles;
    }


    private void initProfile(Profile profile) {
        synchronized (this) {
            Profile newProfile = profile.clone();
            profiles.add(newProfile);
            updateConditionRegistrations(null, newProfile);
            notifyOnProfileChangedListeners(newProfile, ChangedStatus.UPDATED);
        }
    }

    private void onConditionStateChanged(ConditionStateChangedEvent event) {
        synchronized (this) {
            Profile profile = getProfileOrigin(event.getProfileId());
            if (profile == null) return;
            boolean conditionEventHandled = false;
            boolean profileActive = false;
            for (ConditionSet conditionSet : profile.getConditionSets()) {
                boolean conditionSetActive = true;
                for (Condition condition : conditionSet.getConditions()) {
                    if (!conditionEventHandled && condition.getId().equals(event.getConditionId())) {
                        condition.setActive(event.isActive());
                        conditionEventHandled = true;
                    }
                    conditionSetActive = conditionSetActive && condition.isActive();
                }
                conditionSetActive = conditionSetActive && conditionSet.getConditions().size() != 0;
                conditionSet.setActive(conditionSetActive);
                profileActive = profileActive || conditionSetActive;
            }
            profile.setActive(profileActive);
            notifyOnProfileChangedListeners(profile, ChangedStatus.UPDATED);
        }
    }

    private void updateConditionRegistrations(Profile oldProfile, Profile newProfile) {
        complexConditionChecker.updateConditionRegistrations(
                oldProfile != null ? oldProfile.clone() : null,
                newProfile != null ? newProfile.clone() : null);
    }

    private void notifyOnProfileChangedListeners(Profile profile, ChangedStatus status) {
        ProfileChangedEvent profileChangedEvent = new ProfileChangedEvent(profile.clone(), status);
        settingManager.onProfileChanged(profileChangedEvent);
        profileChangedSubject.onNext(profileChangedEvent);
    }

    @Nullable
    private Profile getProfileOrigin(String id) {
        return StreamSupport.stream(profiles)
                .filter(profile -> profile.getId().equals(id))
                .reduce(null, (prev, next) -> next);
    }

    private void initListeners() {
        complexConditionChecker.observeConditionStateChanged()
                .subscribe(this::onConditionStateChanged);
        settingManager.observeGlogalProfileChanged()
                .subscribe(this::onGlobalProfileChanged);
    }

}
