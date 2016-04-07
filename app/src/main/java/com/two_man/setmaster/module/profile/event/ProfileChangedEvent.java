package com.two_man.setmaster.module.profile.event;

import com.two_man.setmaster.entity.Profile;

/**
 *
 */
public class ProfileChangedEvent {
    private Profile profile;
    private final ChangedStatus updated;

    public ProfileChangedEvent(Profile profile, ChangedStatus updated) {
        this.profile = profile;
        this.updated = updated;
    }
}
