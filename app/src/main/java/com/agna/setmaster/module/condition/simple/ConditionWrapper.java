package com.agna.setmaster.module.condition.simple;

import com.agna.setmaster.entity.condition.Condition;

/**
 *
 */
public class ConditionWrapper<C extends Condition> {
    private C condition;
    private String profileId;

    public ConditionWrapper(C condition, String profileId) {
        this.condition = condition;
        this.profileId = profileId;
    }

    public C getCondition() {
        return condition;
    }

    public String getProfileId() {
        return profileId;
    }
}
