package com.two_man.setmaster.module.profile;

import android.content.Context;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;

/**
 *
 */
public class DefaultProfileCreator {

    private Context appContext;

    public DefaultProfileCreator(Context appContext) {
        this.appContext = appContext;
    }

    public Profile createGlobal(){
        Profile globalProfile = new Profile("Global", R.drawable.ic_profile_world);
        globalProfile.setPriority(Profile.PRIORITY_GLOBAL);
        globalProfile.setActive(true);
        return globalProfile;
    }
}
