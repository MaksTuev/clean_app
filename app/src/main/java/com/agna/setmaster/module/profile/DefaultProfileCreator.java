package com.agna.setmaster.module.profile;

import android.content.Context;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.util.ProfileIconHelper;

import javax.inject.Inject;

/**
 *
 */
@PerApplication
public class DefaultProfileCreator {

    private Context appContext;

    @Inject
    public DefaultProfileCreator(Context appContext) {
        this.appContext = appContext;
    }

    public Profile createGlobal() {
        Profile globalProfile = new Profile("Global", ProfileIconHelper.getGlobalProfileIconId());
        globalProfile.setPriority(Profile.PRIORITY_GLOBAL);
        globalProfile.setActive(true);
        return globalProfile;
    }
}
