package com.agna.setmaster.ui.base.activity;

import android.support.v7.app.AppCompatActivity;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.module.condition.ComplexConditionChecker;
import com.agna.setmaster.module.condition.simple.SimpleConditionChecker;
import com.agna.setmaster.module.profile.ProfileService;
import com.agna.setmaster.module.setting.SettingManager;
import com.agna.setmaster.module.setting.applyer.SettingApplier;
import com.agna.setmaster.ui.base.fragment.BaseFragmentView;
import com.agna.setmaster.ui.navigation.Navigator;

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
    ComplexConditionChecker conditionChecker();
    Navigator navigator();
    AppCompatActivity activity();

}
