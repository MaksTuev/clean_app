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
package com.agna.setmaster.util;

import android.support.annotation.DrawableRes;

import com.agna.setmaster.R;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ProfileIconHelper {

    private static List<Integer> icons = Arrays.asList(

            R.drawable.ic_profile_world,
            R.drawable.ic_profile_city,
            R.drawable.ic_profile_home,
            R.drawable.ic_profile_moon,
            R.drawable.ic_profile_mountains,
            R.drawable.ic_profile_school,
            R.drawable.ic_profile_sun,
            R.drawable.ic_profile_speaker,
            R.drawable.ic_profile_sea,
            R.drawable.ic_profile_restaurant,
            R.drawable.ic_profile_night,
            R.drawable.ic_profile_world_2,
            R.drawable.ic_profile_library,
            R.drawable.ic_profile_business,
            R.drawable.ic_profile_bar,
            R.drawable.ic_profile_bagage,
            R.drawable.ic_profile_airplanemode_on
    );

    @DrawableRes
    public static int getIconRes(int iconId){
        return icons.get(iconId);
    }

    public static List<Integer> getAll() {
        return icons;
    }

    public static int getGlobalProfileIconId() {
        return icons.get(0);
    }
}
