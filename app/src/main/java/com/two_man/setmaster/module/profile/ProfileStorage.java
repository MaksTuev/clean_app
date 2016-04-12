package com.two_man.setmaster.module.profile;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.setting.MediaVolumeSetting;
import com.two_man.setmaster.entity.setting.RingSetting;
import com.two_man.setmaster.entity.setting.Setting;

import java.util.ArrayList;

import rx.Observable;

/**
 *
 */
public class ProfileStorage {

    public ProfileStorage() {
    }

    public void add(Profile profile) {

    }

    public void remove(Profile profile) {

    }

    public void update(Profile newProfile) {

    }

    public Observable<ArrayList<Profile>> getAllProfiles(){
        ArrayList<Profile> result = new ArrayList<>();
        result.add(mockProfile1());
        result.add(mockProfile2());
        return Observable.just(result);

    }

    private Profile mockProfile1() {
        Profile p = new Profile("Test1", R.drawable.ic_profile_world);
        ArrayList<Setting> settings = new ArrayList<>();
        settings.add(new RingSetting(0.7f));
        settings.add(new MediaVolumeSetting(0.0f));
        p.setSettings(settings);
        p.setActive(true);
        return p;
    }
    private Profile mockProfile2() {
        Profile p = new Profile("Test1", R.drawable.ic_profile_airplanemode_on);
        ArrayList<Setting> settings = new ArrayList<>();
        settings.add(new MediaVolumeSetting(1.0f));
        p.setSettings(settings);
        p.setActive(false);
        return p;
    }
}
