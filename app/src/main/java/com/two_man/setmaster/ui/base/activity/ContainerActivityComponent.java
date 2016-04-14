package com.two_man.setmaster.ui.base.activity;

import android.support.v7.app.AppCompatActivity;

import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.module.condition.ConditionChecker;
import com.two_man.setmaster.module.condition.simple.SimpleConditionChecker;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.setting.SettingManager;
import com.two_man.setmaster.module.setting.applyer.SettingApplier;
import com.two_man.setmaster.ui.app.AppComponent;
import com.two_man.setmaster.ui.base.fragment.BaseFragmentView;
import com.two_man.setmaster.ui.navigation.Navigator;

import java.util.ArrayList;
import java.util.Map;

import dagger.Component;


/**
 * компонет activity, используемый в качестве {@link Component#dependencies()} в компонентах экранов,
 * у которых view наследуется от {@link BaseFragmentView}
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ContainerActivityComponent {
    Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers();
    Map<Class<? extends Setting>, SettingApplier> settingAppliers();
    ArrayList<Class<? extends Setting>> settingsTypes();
    SettingManager settingManager();

    ProfileService profileService();
    ConditionChecker conditionChecker();
    Navigator navigator();
    AppCompatActivity activity();

}
