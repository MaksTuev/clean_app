package com.agna.setmaster.ui.screen.condition.wifi;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ChangeWifiConditionModule.class})
@PerActivity
public interface ChangeWifiConditionComponent {
    void inject(ChangeWifiConditionActivity activity);
}
