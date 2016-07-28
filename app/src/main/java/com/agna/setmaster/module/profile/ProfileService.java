package com.agna.setmaster.module.profile;

import android.support.annotation.Nullable;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.ConditionSet;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.module.condition.ComplexConditionChecker;
import com.agna.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.agna.setmaster.module.profile.event.ChangedStatus;
import com.agna.setmaster.module.profile.event.ProfileChangedEvent;
import com.agna.setmaster.module.storage.ProfileStorage;
import com.agna.setmaster.util.CloneUtil;
import com.agna.setmaster.util.rx.SimpleOnSubscribe;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import java8.util.stream.StreamSupport;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 */
@PerApplication
public class ProfileService {

    private ComplexConditionChecker complexConditionChecker;
    private ProfileStorage profileStorage;
    private DefaultProfileCreator defaultProfileCreator;

    private ArrayList<Profile> profiles = new ArrayList<>();

    private SimpleOnSubscribe<ProfileChangedEvent> profileChangedOnSubscribe = new SimpleOnSubscribe<>();;

    @Inject
    public ProfileService(ComplexConditionChecker complexConditionChecker,
                          ProfileStorage profileStorage,
                          DefaultProfileCreator defaultProfileCreator) {
        this.complexConditionChecker = complexConditionChecker;
        this.profileStorage = profileStorage;
        this.defaultProfileCreator = defaultProfileCreator;
        initListeners();
    }

    public Observable<Void> initialize(){
        return profileStorage.getAllProfiles()
                .map(profiles -> initGlobalProfile(profiles))
                .flatMap(Observable::from)
                .doOnNext(this::initProfile)
                .toList()
                .flatMap(profiles -> Observable.just(null));
    }

    private  ArrayList<Profile> initGlobalProfile(ArrayList<Profile> profiles) {
        Profile globalProfile = null;
        for(Profile profile : profiles){
            if(profile.isGlobal()){
                globalProfile = profile;
                profiles.remove(profile);
                break;
            }
        }
        if(globalProfile == null){
            globalProfile = defaultProfileCreator.createGlobal();
            profileStorage.add(globalProfile);
        }
        globalProfile.setActive(true);
        this.profiles.add(globalProfile);
        notifyOnProfileChangedListeners(globalProfile, ChangedStatus.UPDATED);
        return profiles;
    }

    public Observable<ProfileChangedEvent> observeProfileChanged() {
        return Observable.create(profileChangedOnSubscribe)
                .subscribeOn(Schedulers.io());
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

    public void updateGlobalProfile(Profile globalProfile) {
        synchronized (this) {
            assert globalProfile.isGlobal();
            Profile newProfile = globalProfile.clone();
            Profile oldProfile = getProfileOrigin(newProfile.getId());
            profiles.remove(oldProfile);
            profiles.add(newProfile);
            profileStorage.update(newProfile);
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
            for(Profile profile: profiles){
                if(profile.isGlobal()){
                    profiles.remove(profile);
                    break;
                }
            }
            Collections.sort(profiles);
            return profiles;
        }
    }

    private void initProfile(Profile profile){
        synchronized (this){
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
        profileChangedOnSubscribe.emit(new ProfileChangedEvent(profile.clone(), status));
    }

    @Nullable
    private Profile getProfileOrigin(String id) {
        return StreamSupport.stream(profiles)
                .filter(profile -> profile.getId().equals(id))
                .reduce(null, (prev, next)->next);
    }

    private void initListeners() {
        complexConditionChecker.observeConditionStateChanged()
                .subscribe(this::onConditionStateChanged);
    }

}
