package com.agna.setmaster.module.setting;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.module.profile.event.ChangedStatus;
import com.agna.setmaster.module.profile.event.ProfileChangedEvent;
import com.agna.setmaster.module.setting.applyer.SettingApplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 *
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

    public Observable<Profile> observeGlogalProfileChanged(){
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
                if(profilesWithSetting.size() == 1 && !appliedProfilesWithSetting.isGlobal()){
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
                    if(newAppliedProfilesWithSetting.isGlobal()){
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
        if(globalProfile != null) {
            Setting currentSetting = settingAppliers.get(settingType).getCurrent();
            globalProfile.addSetting(currentSetting);
            globalProfileChangedSubject.onNext(globalProfile);
        }
    }

    private Profile getGlobalProfile(){
        for(Profile profile: activeProfiles){
            if(profile.isGlobal()){
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
