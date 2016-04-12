package com.two_man.setmaster.module.setting;

import com.two_man.setmaster.entity.setting.MediaVolumeSetting;
import com.two_man.setmaster.entity.setting.RingSetting;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.applyer.MediaVolumeSettingAppyer;
import com.two_man.setmaster.module.setting.applyer.RingSettingAppyer;
import com.two_man.setmaster.module.setting.applyer.SettingApplier;
import com.two_man.setmaster.ui.app.PerApplication;

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
    Map<Class<? extends Setting>, SettingApplier> provideSettingAppliers(){
        Map<Class<? extends Setting>, SettingApplier> settingAppliers = new HashMap<>();
        settingAppliers.put(RingSetting.class, new RingSettingAppyer());
        settingAppliers.put(MediaVolumeSetting.class, new MediaVolumeSettingAppyer());
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
}
