package com.two_man.setmaster.entity;

import android.support.annotation.DrawableRes;

import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.util.CloneUtil;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 */
public class Profile implements Cloneable {
    private String id;
    private String name;
    @DrawableRes
    private int imageResId;
    private boolean active;
    private ArrayList<ConditionSet> conditionSets;
    private ArrayList<Setting> settings;

    public Profile(String name, @DrawableRes int image) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.imageResId = image;
    }


    public Profile(String id,
                   String name,
                   int imageResId,
                   boolean active,
                   ArrayList<ConditionSet> conditionSets,
                   ArrayList<Setting> settings) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.active = active;
        this.conditionSets = conditionSets;
        this.settings = settings;
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

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public ArrayList<ConditionSet> getConditionSets() {
        return conditionSets;
    }

    public void setConditionSets(ArrayList<ConditionSet> conditionSets) {
        this.conditionSets = conditionSets;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Setting> settings) {
        this.settings = settings;
    }

    @Override
    public Profile clone(){
        return new Profile(id, name, imageResId, active,
                CloneUtil.cloneConditionSetList(conditionSets),
                CloneUtil.cloneSettingList(settings));
    }
}
