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
package com.agna.setmaster.interactor.setting;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.domain.setting.MediaVolumeSetting;
import com.agna.setmaster.domain.setting.RingSetting;
import com.agna.setmaster.domain.setting.Setting;
import com.agna.setmaster.interactor.setting.applyer.MediaVolumeSettingApplier;
import com.agna.setmaster.interactor.setting.applyer.RingSettingApplier;
import com.agna.setmaster.interactor.setting.applyer.SettingApplier;

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
    Map<Class<? extends Setting>, SettingApplier> provideSettingAppliers(
            RingSettingApplier ringSettingApplier,
            MediaVolumeSettingApplier mediaVolumeSettingApplier) {
        Map<Class<? extends Setting>, SettingApplier> settingAppliers = new HashMap<>();
        settingAppliers.put(RingSetting.class, ringSettingApplier);
        settingAppliers.put(MediaVolumeSetting.class, mediaVolumeSettingApplier);
        return settingAppliers;
    }

    @Provides
    @PerApplication
    ArrayList<Class<? extends Setting>> provideSettingTypes() {
        ArrayList<Class<? extends Setting>> settingTypes = new ArrayList<>();
        settingTypes.add(RingSetting.class);
        settingTypes.add(MediaVolumeSetting.class);
        return settingTypes;
    }
}
