package com.agna.setmaster.module.setting;

import android.content.Context;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.setting.MediaVolumeSetting;
import com.agna.setmaster.entity.setting.RingSetting;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.module.profile.ProfileService;
import com.agna.setmaster.module.setting.applyer.MediaVolumeSettingApplier;
import com.agna.setmaster.module.setting.applyer.RingSettingApplier;
import com.agna.setmaster.module.setting.applyer.SettingApplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class SettingModule {

    @Provides
    @PerApplication
    SettingManager provideSettingManager (Map<Class<? extends Setting>, SettingApplier> settingAppliers,
                                          ArrayList<Class<? extends Setting>> settingTypes,
                                          ProfileService profileService){
        return new SettingManager(settingAppliers, settingTypes, profileService);
    }

    @Provides
    @PerApplication
    Map<Class<? extends Setting>, SettingApplier> provideSettingAppliers(
            RingSettingApplier ringSettingApplier,
            MediaVolumeSettingApplier mediaVolumeSettingApplier){
        Map<Class<? extends Setting>, SettingApplier> settingAppliers = new HashMap<>();
        settingAppliers.put(RingSetting.class, ringSettingApplier);
        settingAppliers.put(MediaVolumeSetting.class, mediaVolumeSettingApplier);
        return settingAppliers;
    }

    @Provides
    @PerApplication
    ArrayList<Class<? extends Setting>> provideSettingTypes(){
        ArrayList<Class<? extends Setting>> settingTypes = new ArrayList<>();
        settingTypes.add(RingSetting.class);
        settingTypes.add(MediaVolumeSetting.class);
        return settingTypes;
    }

    @Provides
    @PerApplication
    RingSettingApplier provideRingSettingApplier(Context context){
        return new RingSettingApplier(context);
    }

    @Provides
    @PerApplication
    MediaVolumeSettingApplier provideMediaVolumeSettingAppyer(Context context){
        return new MediaVolumeSettingApplier(context);
    }
}
