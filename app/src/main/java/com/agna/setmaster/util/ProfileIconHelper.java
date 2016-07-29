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
