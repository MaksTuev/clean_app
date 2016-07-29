package com.agna.setmaster.ui.navigation;

import android.support.v7.app.AppCompatActivity;

import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.condition.TimeCondition;
import com.agna.setmaster.entity.condition.WiFiCondition;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.screen.condition.time.ChangeTimeConditionActivity;
import com.agna.setmaster.ui.screen.condition.wifi.ChangeWifiConditionActivity;
import com.agna.setmaster.ui.screen.editprofile.EditProfileActivity;
import com.agna.setmaster.ui.screen.main.MainActivity;
import com.agna.setmaster.ui.screen.profile.ProfileActivity;

import javax.inject.Inject;

/**
 *
 */
@PerScreen
public class Navigator {

    private AppCompatActivity activity;

    @Inject
    public Navigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void openMain() {
        MainActivity.start(activity);
    }

    public void openNewProfile() {
        EditProfileActivity.start(activity, null);
    }

    public void openProfile(Profile profile) {
        ProfileActivity.start(activity, profile);
    }

    public void openEditProfile(Profile profile) {
        EditProfileActivity.start(activity, profile);
    }

    public void openChangeCondition(Condition condition, Profile profile) {
        if (condition instanceof TimeCondition) {
            ChangeTimeConditionActivity.start(activity, (TimeCondition) condition, profile);
        } else if (condition instanceof WiFiCondition) {
            ChangeWifiConditionActivity.start(activity, (WiFiCondition) condition, profile);
        } else {
            throw new IllegalArgumentException("Unsupported condition: " + condition);
        }
    }
}
