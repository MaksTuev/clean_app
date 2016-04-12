package com.two_man.setmaster.ui.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;

public class ProfileViewUtil {
    public static int getProfileStatusText(Profile profile) {
        if(profile.isActive()){
            return R.string.profile_status_active;
        } else {
            return R.string.profile_status_inactive;
        }
    }

    public static int getProfileAccentColor(Context context, Profile profile) {
        boolean activeProfile = profile!=null && profile.isActive();
        int backgroundColor = activeProfile
                ? ContextCompat.getColor(context, R.color.profile_active_bg)
                : ContextCompat.getColor(context, R.color.profile_inactive_bg);
        return backgroundColor;
    }
}
