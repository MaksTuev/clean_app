package com.two_man.setmaster.ui.screen.condition.time;

import com.two_man.setmaster.ui.app.AppComponent;
import com.two_man.setmaster.ui.base.activity.ActivityModule;
import com.two_man.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
@PerActivity
public interface ChangeTimeConditionComponent {
    void inject(ChangeTimeConditionActivity activity);
}
