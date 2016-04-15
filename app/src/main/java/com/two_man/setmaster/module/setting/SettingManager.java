package com.two_man.setmaster.module.setting;

import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.profile.event.ChangedStatus;
import com.two_man.setmaster.module.profile.event.ProfileChangedEvent;
import com.two_man.setmaster.module.setting.applyer.SettingApplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 *
 */
public class SettingManager {
    private Map<Class<? extends Setting>, SettingApplier> settingAppliers;
    private List<Class<? extends Setting>> settingTypes;
    private final ProfileService profileService;

    private List<Profile> activeProfiles = new ArrayList<>();

    public SettingManager(Map<Class<? extends Setting>, SettingApplier> settingAppliers,
                          ArrayList<Class<? extends Setting>> settingTypes,
                          ProfileService profileService) {
        this.settingAppliers = settingAppliers;
        this.settingTypes = settingTypes;
        this.profileService = profileService;
        profileService.observeProfileChanged()
                .subscribe(this::onProfileChanged);
    }

    private void onProfileChanged(ProfileChangedEvent event) {
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
        profileService.updateGlobalProfile(globalProfile);
    }

    private void saveOldSettingInGlobalProfile(Class<? extends Setting> settingType) {
        Profile globalProfile = getGlobalProfile();
        if(globalProfile != null) {
            Setting currentSetting = settingAppliers.get(settingType).getCurrent();
            globalProfile.addSetting(currentSetting);
            profileService.updateGlobalProfile(globalProfile);
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
