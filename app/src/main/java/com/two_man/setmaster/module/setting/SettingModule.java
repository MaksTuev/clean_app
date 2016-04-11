package com.two_man.setmaster.module.setting;

import com.two_man.setmaster.entity.setting.RingSetting;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.applyer.RingSettingAppyer;
import com.two_man.setmaster.module.setting.applyer.SettingApplier;
import com.two_man.setmaster.ui.app.PerApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                                          List<Class<? extends Setting>> settingTypes,
                                          ProfileService profileService){
        return new SettingManager(settingAppliers, settingTypes, profileService);
    }

    @Provides
    @PerApplication
    Map<Class<? extends Setting>, SettingApplier> provideSettingAppliers(){
        Map<Class<? extends Setting>, SettingApplier> settingAppliers = new HashMap<>();
        settingAppliers.put(RingSetting.class, new RingSettingAppyer());
        return settingAppliers;
    }

    @Provides
    @PerApplication
    List<Class<? extends Setting>> provideSettingTypes(){
        List<Class<? extends Setting>> settingTypes = new ArrayList<>();
        settingTypes.add(RingSetting.class);
        return settingTypes;
    }
}
