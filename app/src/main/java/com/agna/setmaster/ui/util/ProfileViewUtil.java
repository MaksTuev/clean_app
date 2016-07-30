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
package com.agna.setmaster.ui.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.Profile;

public class ProfileViewUtil {
    public static int getProfileStatusText(Profile profile) {
        if (profile.isActive()) {
            return R.string.profile_status_active;
        } else {
            return R.string.profile_status_inactive;
        }
    }

    public static int getProfileAccentColor(Context context, Profile profile) {
        boolean activeProfile = profile != null && profile.isActive();
        int backgroundColor = activeProfile
                ? ContextCompat.getColor(context, R.color.profile_active_bg)
                : ContextCompat.getColor(context, R.color.profile_inactive_bg);
        return backgroundColor;
    }
}
