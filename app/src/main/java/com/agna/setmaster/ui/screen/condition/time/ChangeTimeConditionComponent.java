package com.agna.setmaster.ui.screen.condition.time;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
@PerActivity
public interface ChangeTimeConditionComponent {
    void inject(ChangeTimeConditionActivity activity);
}
