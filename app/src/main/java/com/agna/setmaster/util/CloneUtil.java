package com.agna.setmaster.util;

import com.agna.setmaster.entity.ConditionSet;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.setting.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CloneUtil {

    public static ArrayList<Setting> cloneSettingList(List<Setting> collection) {
        ArrayList<Setting> clone = new ArrayList<>(collection.size());
        for (Setting item : collection) clone.add(item.clone());
        return clone;
    }

    public static ArrayList<ConditionSet> cloneConditionSetList(List<ConditionSet> collection) {
        ArrayList<ConditionSet> clone = new ArrayList<>(collection.size());
        for (ConditionSet item : collection) clone.add(item.clone());
        return clone;
    }

    public static ArrayList<Condition> cloneConditionList(List<Condition> collection) {
        ArrayList<Condition> clone = new ArrayList<>(collection.size());
        for (Condition item : collection) clone.add(item.clone());
        return clone;
    }

    public static ArrayList<Profile> cloneProfiles(List<Profile> collection) {
        ArrayList<Profile> clone = new ArrayList<>(collection.size());
        for (Profile item : collection) clone.add(item.clone());
        return clone;
    }
}
