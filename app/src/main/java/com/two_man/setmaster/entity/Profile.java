package com.two_man.setmaster.entity;

import android.support.annotation.DrawableRes;

import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.util.CloneUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java8.util.stream.StreamSupport;

/**
 *
 */
public class Profile implements Cloneable, Serializable{
    private static final int PRIORITY_GLOBAL = 0;
    private static final int PRIORITY_DEFAULT = 1;

    private String id;
    private String name;
    @DrawableRes
    private int imageResId;
    private int priority = PRIORITY_DEFAULT;
    private boolean active;
    private ArrayList<ConditionSet> conditionSets = new ArrayList<>();
    private ArrayList<Setting> settings = new ArrayList<>();

    public Profile(String name, @DrawableRes int image) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.imageResId = image;
        conditionSets.add(new ConditionSet());
    }


    private Profile(String id,
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

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
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

    public <S extends Setting> S getSetting(Class<S> settingClass){
        return (S)StreamSupport.stream(settings)
                .filter(setting -> setting.getClass() == settingClass)
                .reduce(null, (prev, next) -> next);
    }

    public void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public void updateSetting(Setting updatedSetting) {
        for(int i = 0; i< settings.size(); i++){
            Setting setting = settings.get(i);
            if(updatedSetting.getId().equals(setting.getId())){
                settings.remove(i);
                settings.add(i, updatedSetting);
                break;
            }
        }
    }

    public void deleteSetting(Setting deletedSetting) {
        for(int i = 0; i< settings.size(); i++){
            Setting setting = settings.get(i);
            if(deletedSetting.getId().equals(setting.getId())){
                settings.remove(i);
                break;
            }
        }
    }

    public ConditionSet getConditionSet(String conditionSetId) {
        return StreamSupport.stream(conditionSets)
                .filter(conditionSet -> conditionSet.getId().equals(conditionSetId))
                .reduce(null, (prev, next)->next);
    }

    @Override
    public Profile clone(){
        return new Profile(id, name, imageResId, active,
                CloneUtil.cloneConditionSetList(conditionSets),
                CloneUtil.cloneSettingList(settings));
    }

    public void updateCondition(Condition newCondition) {
        for(ConditionSet conditionSet : conditionSets){
            boolean updated = false;
            List<Condition> conditions = conditionSet.getConditions();
            for(int i = 0; i<conditions.size(); i++){
                Condition condition = conditions.get(i);
                if(condition.getId().equals(newCondition.getId())){
                    conditions.remove(i);
                    conditions.add(i, newCondition);
                    updated = true;
                    break;
                }
            }
            if(updated){
                return;
            }
        }
        throw new IllegalArgumentException("condition "+ newCondition+ "not exist");
    }

    public void deleteConditionSet(ConditionSet conditionSetForDelete) {
        for(ConditionSet conditionSet : conditionSets){
            if(conditionSet.getId().equals(conditionSetForDelete.getId())){
                conditionSets.remove(conditionSet);
                return;
            }
        }
        throw new IllegalArgumentException("conditionSet "+ conditionSetForDelete+ "not exist");
    }
}
