package com.two_man.setmaster.domainOld.servise.repository;

import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.setting.Setting;

/**
 *
 */
public interface Repository {
    Profile getProfile(int profileId);
    void createProfile(Profile profile);
    void updateProfile(Profile profile);
    void deleteProfile(int profileId);

    void createConditionSet(int profileId, ConditionSet conditionSet);
    void createCondition(int conditionSetId, Condition condition);
    void updateCondition(Condition condition);
    void deleteCondition(int conditionId);

    void createSetting(Setting setting);
    void updateSetting(Setting setting);
    void deleteSetting(int settingId);
}
