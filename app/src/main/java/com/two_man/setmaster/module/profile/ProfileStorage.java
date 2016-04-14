package com.two_man.setmaster.module.profile;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.condition.DayOfWeek;
import com.two_man.setmaster.entity.condition.TimeCondition;
import com.two_man.setmaster.entity.condition.WiFiCondition;
import com.two_man.setmaster.entity.setting.MediaVolumeSetting;
import com.two_man.setmaster.entity.setting.RingSetting;
import com.two_man.setmaster.entity.setting.Setting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public Observable<ArrayList<Profile>> getAllProfiles() {
        ArrayList<Profile> result = new ArrayList<>();
        result.add(mockProfile1());
        result.add(mockProfile2());
        return Observable.just(result);

    }

    private Profile mockProfile1() {
        try {
            Profile p = new Profile("Test1", R.drawable.ic_profile_world);
            ArrayList<Setting> settings = new ArrayList<>();
            settings.add(new RingSetting(0.7f));
            settings.add(new MediaVolumeSetting(0.0f));
            p.setSettings(settings);
            p.setActive(true);
            ConditionSet conditionSet = p.getConditionSets().get(0);

            WiFiCondition wiFiCondition = new WiFiCondition("testnet");
            wiFiCondition.setActive(true);
            conditionSet.addCondition(wiFiCondition);

            ArrayList<DayOfWeek> days = new ArrayList<>();
            days.add(DayOfWeek.MONDAY);
            days.add(DayOfWeek.TUESDAY);
            days.add(DayOfWeek.WEDNESDAY);
            days.add(DayOfWeek.FRIDAY);
            days.add(DayOfWeek.SATURDAY);
            SimpleDateFormat f = new SimpleDateFormat("HH:mm");
            Date from = f.parse("12:00");
            Date to = f.parse("20:10");
            TimeCondition timeCondition = new TimeCondition(from, to, days);
            conditionSet.addCondition(timeCondition);
            return p;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private Profile mockProfile2() {
        try {
            Profile p = new Profile("Test1", R.drawable.ic_profile_airplanemode_on);
            ArrayList<Setting> settings = new ArrayList<>();
            settings.add(new MediaVolumeSetting(1.0f));
            p.setSettings(settings);
            p.setActive(false);

            ConditionSet conditionSet = p.getConditionSets().get(0);
            ArrayList<DayOfWeek> days = new ArrayList<>();
            days.add(DayOfWeek.MONDAY);
            days.add(DayOfWeek.WEDNESDAY);
            days.add(DayOfWeek.FRIDAY);
            days.add(DayOfWeek.SATURDAY);
            days.add(DayOfWeek.SUNDAY);
            SimpleDateFormat f = new SimpleDateFormat("HH:mm");
            Date from = null;
            from = f.parse("12:00");

            Date to = f.parse("20:10");
            TimeCondition timeCondition = new TimeCondition(from, to, days);
            timeCondition.setActive(true);
            conditionSet.addCondition(timeCondition);

            ConditionSet secondConditionSet = new ConditionSet();
            WiFiCondition wiFiCondition = new WiFiCondition("testnet2");
            secondConditionSet.addCondition(wiFiCondition);
            p.addConditionSet(secondConditionSet);
            return p;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
