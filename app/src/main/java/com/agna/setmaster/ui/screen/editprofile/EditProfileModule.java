package com.agna.setmaster.ui.screen.editprofile;

import com.agna.setmaster.R;
import com.agna.setmaster.ui.base.PerScreen;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class EditProfileModule {

    public EditProfileModule() {
    }

    @Provides
    @PerScreen
    List<Integer> provideIcons() {
        return Arrays.asList(
                R.drawable.ic_profile_city,
                R.drawable.ic_profile_home,
                R.drawable.ic_profile_moon,
                R.drawable.ic_profile_mountains,
                R.drawable.ic_profile_school,
                R.drawable.ic_profile_sun,
                R.drawable.ic_profile_world,
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
    }
}
