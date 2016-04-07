package com.two_man.setmaster.module.condition.simple;

/**
 *
 */
public class ConditionStateChangedEvent {
    private String profileId;
    private String conditionId;
    private boolean active;

    public ConditionStateChangedEvent(String profileId, String conditionId, boolean active) {
        this.profileId = profileId;
        this.conditionId = conditionId;
        this.active = active;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getConditionId() {
        return conditionId;
    }

    public boolean isActive() {
        return active;
    }
}
