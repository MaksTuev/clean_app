package com.two_man.setmaster.module.condition;

import com.two_man.setmaster.entity.condition.Condition;

/**
 *
 */
public class ConditionWrapper {
    private Condition condition;
    private String profileId;

    public ConditionWrapper(Condition condition, String profileId) {
        this.condition = condition;
        this.profileId = profileId;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getProfileId() {
        return profileId;
    }
}
