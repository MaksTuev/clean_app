package com.two_man.setmaster.ui.app;

import android.content.Context;

import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.interactor.InitializeAppInteractor;
import com.two_man.setmaster.module.condition.ConditionChecker;
import com.two_man.setmaster.module.condition.simple.SimpleConditionChecker;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.SettingManager;
import com.two_man.setmaster.module.setting.applyer.SettingApplier;

import java.util.ArrayList;
import java.util.Map;

import dagger.Component;

@Component(modules = AppModule.class)
@PerApplication
public interface AppComponent {

    Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers();
    Map<Class<? extends Setting>, SettingApplier> settingAppliers();
    ArrayList<Class<? extends Setting>> settingsTypes();
    ArrayList<Class<? extends Condition>> conditionsTypes();
    SettingManager settingManager();
    ProfileService profileService();
    ConditionChecker conditionChecker();
    InitializeAppInteractor initializeAppInteractor();
    Context appContext();
}
