package com.agna.setmaster.entity;

import android.support.annotation.DrawableRes;

import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.util.CloneUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java8.util.stream.StreamSupport;

/**
 *
 */
public class Profile implements Cloneable, Serializable, Comparable<Profile> {
    public static final int PRIORITY_GLOBAL = 0;
    private static final int PRIORITY_DEFAULT = 1;

    private String id;
    private String name;
    @DrawableRes
    private int iconId;
    private int priority = PRIORITY_DEFAULT;
    private boolean active;
    private ArrayList<ConditionSet> conditionSets = new ArrayList<>();
    private ArrayList<Setting> settings = new ArrayList<>();

    public Profile(String name, @DrawableRes int image) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.iconId = image;
        conditionSets.add(new ConditionSet());
    }


    private Profile(String id,
                    String name,
                    int iconId,
                    boolean active,
                    int priority,
                    ArrayList<ConditionSet> conditionSets,
                    ArrayList<Setting> settings) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.active = active;
        this.priority = priority;
        this.conditionSets = conditionSets;
        this.settings = settings;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public ArrayList<ConditionSet> getConditionSets() {
        return conditionSets;
    }

    public void addConditionSet(ConditionSet conditionSet) {
        this.conditionSets.add(conditionSet);
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Setting> settings) {
        this.settings = settings;
    }

    public <S extends Setting> S getSetting(Class<S> settingClass) {
        return (S) StreamSupport.stream(settings)
                .filter(setting -> setting.getClass() == settingClass)
                .reduce(null, (prev, next) -> next);
    }

    public void addSetting(Setting setting) {
        Setting oldSetting = getSetting(setting.getClass());
        if (oldSetting != null) {
            settings.remove(oldSetting);
        }
        this.settings.add(setting);
    }

    public void updateSetting(Setting updatedSetting) {
        for (int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);
            if (updatedSetting.getId().equals(setting.getId())) {
                settings.remove(i);
                settings.add(i, updatedSetting);
                break;
            }
        }
    }

    public void deleteSetting(Setting deletedSetting) {
        for (int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);
            if (deletedSetting.getId().equals(setting.getId())) {
                settings.remove(i);
                break;
            }
        }
    }

    public ConditionSet getConditionSet(String conditionSetId) {
        return StreamSupport.stream(conditionSets)
                .filter(conditionSet -> conditionSet.getId().equals(conditionSetId))
                .reduce(null, (prev, next) -> next);
    }

    @Override
    public Profile clone() {
        return new Profile(id, name, iconId, active, priority,
                CloneUtil.cloneConditionSetList(conditionSets),
                CloneUtil.cloneSettingList(settings));
    }

    public void updateCondition(Condition newCondition) {
        for (ConditionSet conditionSet : conditionSets) {
            boolean updated = false;
            List<Condition> conditions = conditionSet.getConditions();
            for (int i = 0; i < conditions.size(); i++) {
                Condition condition = conditions.get(i);
                if (condition.getId().equals(newCondition.getId())) {
                    conditions.remove(i);
                    conditions.add(i, newCondition);
                    updated = true;
                    break;
                }
            }
            if (updated) {
                return;
            }
        }
        throw new IllegalArgumentException("condition " + newCondition + "not exist");
    }

    public void deleteConditionSet(ConditionSet conditionSetForDelete) {
        for (ConditionSet conditionSet : conditionSets) {
            if (conditionSet.getId().equals(conditionSetForDelete.getId())) {
                conditionSets.remove(conditionSet);
                return;
            }
        }
        throw new IllegalArgumentException("conditionSet " + conditionSetForDelete + "not exist");
    }

    public void clearActiveState() {
        active = false;
        for (ConditionSet conditionSet : conditionSets) {
            conditionSet.setActive(false);
            for (Condition condition : conditionSet.getConditions()) {
                condition.setActive(false);
            }
        }
    }

    public boolean isGlobal() {
        return priority == PRIORITY_GLOBAL;
    }

    @Override
    public int compareTo(Profile another) {
        int priorityOrder = getPriority() - another.getPriority();
        if (priorityOrder != 0) {
            return priorityOrder;
        } else {
            return getName().compareTo(another.getName());
        }

    }
}
