package com.two_man.setmaster.module.profile;

import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.module.condition.ConditionChecker;
import com.two_man.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.two_man.setmaster.module.profile.event.ChangedStatus;
import com.two_man.setmaster.module.profile.event.ProfileChangedEvent;
import com.two_man.setmaster.util.CloneUtil;
import com.two_man.setmaster.util.rx.SimpleOnSubscribe;

import java.util.ArrayList;

import java8.util.stream.StreamSupport;
import rx.Observable;

/**
 *
 */
public class ProfileService {

    private ConditionChecker conditionChecker;
    private ProfileStorage profileStorage;

    private ArrayList<Profile> profiles = new ArrayList<>();

    private SimpleOnSubscribe<ProfileChangedEvent> profileChangedOnSubscribe;
    private Observable<ProfileChangedEvent> profileChangedObservable;

    public ProfileService(ConditionChecker conditionChecker, ProfileStorage profileStorage) {
        this.conditionChecker = conditionChecker;
        this.profileStorage = profileStorage;
        initObservable();
        initListeners();
    }

    public Observable<Void> initialize(){
        return profileStorage.getAllProfiles()
                .flatMap(Observable::from)
                .doOnNext(this::initProfile)
                .toList()
                .flatMap(profiles -> Observable.just(null));
    }

    public Observable<ProfileChangedEvent> observeProfileChanged() {
        return profileChangedObservable;
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
            return CloneUtil.cloneProfiles(profiles);
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
        conditionChecker.updateConditionRegistrations(
                oldProfile != null ? oldProfile.clone() : null,
                newProfile != null ? newProfile.clone() : null);
    }

    private void notifyOnProfileChangedListeners(Profile profile, ChangedStatus status) {
        profileChangedOnSubscribe.emit(new ProfileChangedEvent(profile.clone(), status));
    }

    private Profile getProfileOrigin(String id) {
        return StreamSupport.stream(profiles)
                .filter(profile -> profile.getId().equals(id))
                .findFirst()
                .get();
    }

    private void initListeners() {
        conditionChecker.observeConditionStateChanged()
                .subscribe(this::onConditionStateChanged);
    }

    private void initObservable() {
        profileChangedOnSubscribe = new SimpleOnSubscribe<>();
        profileChangedObservable = Observable.create(profileChangedOnSubscribe);
    }
}
