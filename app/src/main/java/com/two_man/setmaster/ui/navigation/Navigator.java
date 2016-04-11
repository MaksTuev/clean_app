package com.two_man.setmaster.ui.navigation;

import android.app.Activity;

import com.two_man.setmaster.ui.screen.editprofile.EditProfileActivity;
import com.two_man.setmaster.ui.screen.main.MainActivity;

/**
 *
 */
public class Navigator {

    private Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public void openMain() {
        MainActivity.start(activity);
    }

    public void openNewProfile() {
        EditProfileActivity.start(activity, null);
    }
}
