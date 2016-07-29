package com.agna.setmaster.ui.screen.condition.time;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.dialog.module.ActivityDialogModule;

import dagger.Component;

/**
 *
 */
@Component(dependencies = AppComponent.class, modules = {
        ActivityModule.class,
        ActivityDialogModule.class,
        TimeConditionModule.class})
@PerScreen
public interface ChangeTimeConditionComponent {
    void inject(ChangeTimeConditionActivity activity);
}
