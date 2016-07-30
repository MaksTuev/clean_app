/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.ui.common.navigation;

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
