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
package com.agna.setmaster.app.dagger;

import android.content.Context;

import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.domain.setting.Setting;
import com.agna.setmaster.interactor.initialize.InitializeAppInteractor;
import com.agna.setmaster.interactor.condition.ComplexConditionChecker;
import com.agna.setmaster.interactor.condition.simple.SimpleConditionChecker;
import com.agna.setmaster.interactor.condition.simple.time.TimeBroadcastReceiver;
import com.agna.setmaster.interactor.condition.simple.time.TimeConditionChecker;
import com.agna.setmaster.interactor.condition.simple.wifi.WifiConditionChecker;
import com.agna.setmaster.interactor.condition.simple.wifi.WifiStatusBroadcastReceiver;
import com.agna.setmaster.interactor.profile.DefaultProfileCreator;
import com.agna.setmaster.interactor.profile.ProfileService;
import com.agna.setmaster.interactor.scheduler.SchedulersProvider;
import com.agna.setmaster.interactor.service.AppService;
import com.agna.setmaster.interactor.service.AppServiceInteractor;
import com.agna.setmaster.interactor.service.DeviceBootReceiver;
import com.agna.setmaster.interactor.setting.SettingManager;
import com.agna.setmaster.interactor.setting.applyer.MediaVolumeSettingApplier;
import com.agna.setmaster.interactor.setting.applyer.RingSettingApplier;
import com.agna.setmaster.interactor.setting.applyer.SettingApplier;
import com.agna.setmaster.interactor.storage.db.DataBaseHelper;
import com.agna.setmaster.interactor.storage.db.dao.ProfileDao;

import java.util.ArrayList;
import java.util.Map;

import dagger.Component;

@Component(modules = AppModule.class)
@PerApplication
public interface AppComponent {
    void inject(TimeBroadcastReceiver obj);

    void inject(WifiStatusBroadcastReceiver obj);

    void inject(AppService obj);

    void inject(DeviceBootReceiver obj);

    ComplexConditionChecker conditionChecker();

    Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers();

    TimeConditionChecker timeConditionChecker();

    WifiConditionChecker wifiConditionChecker();

    ArrayList<Class<? extends Condition>> conditionsTypes();

    Map<Class<? extends Setting>, SettingApplier> settingAppliers();

    RingSettingApplier ringSettingApplier();

    MediaVolumeSettingApplier mediaVolumeSettingApplier();

    ArrayList<Class<? extends Setting>> settingsTypes();

    SettingManager settingManager();

    ProfileService profileService();

    DefaultProfileCreator defaultProfileCreator();

    ProfileDao profileDao();

    InitializeAppInteractor initializeAppInteractor();

    DataBaseHelper dataBaseHelper();

    AppServiceInteractor appServiceInteractor();

    Context appContext();

    SchedulersProvider schedulersProvider();

}
