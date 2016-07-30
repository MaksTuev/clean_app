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
package com.agna.setmaster.util;

import com.agna.setmaster.domain.ConditionSet;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.domain.setting.Setting;

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
