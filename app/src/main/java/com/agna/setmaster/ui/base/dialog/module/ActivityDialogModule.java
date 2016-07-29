package com.agna.setmaster.ui.base.dialog.module;

import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.dialog.ActivityDialogManager;
import com.agna.setmaster.ui.base.dialog.DialogManager;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ActivityDialogModule {
    @Provides
    @PerScreen
    DialogManager provideDialogManager(ActivityDialogManager dialogManager) {
        return dialogManager;
    }
}
