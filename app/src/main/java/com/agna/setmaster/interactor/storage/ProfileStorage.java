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
package com.agna.setmaster.interactor.storage;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.interactor.scheduler.SchedulersProvider;
import com.agna.setmaster.interactor.storage.db.dao.ProfileDao;
import com.agna.setmaster.interactor.storage.db.entity.ProfileObj;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 *
 */
@PerApplication
public class ProfileStorage {
    private ProfileDao profileDao;
    private final SchedulersProvider schedulersProvider;

    @Inject
    public ProfileStorage(ProfileDao profileDao, SchedulersProvider schedulersProvider) {
        this.profileDao = profileDao;
        this.schedulersProvider = schedulersProvider;
    }

    public void add(Profile profile) {
        profileDao.saveAsync(new ProfileObj(profile))
                .subscribeOn(schedulersProvider.worker())
                .subscribe(
                        profileObj -> {/*nothing*/},
                        this::handleUnexpectedError);
    }

    private void handleUnexpectedError(Throwable throwable) {
        Timber.e(throwable, "db error");
    }

    public void remove(Profile profile) {
        profileDao.deleteAsync(profile.getId())
                .subscribeOn(schedulersProvider.worker())
                .subscribe(
                        profileObj -> {/*nothing*/},
                        this::handleUnexpectedError);
    }

    public void update(Profile newProfile) {
        profileDao.saveAsync(new ProfileObj(newProfile))
                .subscribeOn(schedulersProvider.worker())
                .subscribe(
                        profileObj -> {/*nothing*/},
                        this::handleUnexpectedError);
    }

    public Observable<ArrayList<Profile>> getAllProfiles() {
        return profileDao.getAllAsync()
                .subscribeOn(schedulersProvider.worker())
                .map(this::transformProfileObjList);

    }

    private ArrayList<Profile> transformProfileObjList(List<ProfileObj> list) {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (ProfileObj profileObj : list) {
            profiles.add(profileObj.getProfile());
        }
        return profiles;
    }
}
