package com.two_man.setmaster.util;

import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.setting.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CloneUtil {

    public static ArrayList<Setting> cloneSettingList(List<Setting> collection){
            ArrayList<Setting> clone = new ArrayList<>(collection.size());
            for(Setting item: collection) clone.add(item.clone());
            return clone;
    }

    public static ArrayList<ConditionSet> cloneConditionSetList(List<ConditionSet> collection){
        ArrayList<ConditionSet> clone = new ArrayList<>(collection.size());
        for(ConditionSet item: collection) clone.add(item.clone());
        return clone;
    }

    public static ArrayList<Condition> cloneConditionList(List<Condition> collection){
        ArrayList<Condition> clone = new ArrayList<>(collection.size());
        for(Condition item: collection) clone.add(item.clone());
        return clone;
    }
}
