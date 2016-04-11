package com.two_man.setmaster.module.profile.event;

import com.two_man.setmaster.entity.Profile;

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
