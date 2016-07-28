package com.agna.setmaster.module.profile.event;

import com.agna.setmaster.entity.Profile;

/**
 *
 */
public class ProfileChangedEvent {
    private Profile profile;
    private final ChangedStatus status;

    public ProfileChangedEvent(Profile profile, ChangedStatus status) {
        this.profile = profile;
        this.status = status;
    }

    public Profile getProfile() {
        return profile;
    }

    public ChangedStatus getStatus() {
        return status;
    }
}
