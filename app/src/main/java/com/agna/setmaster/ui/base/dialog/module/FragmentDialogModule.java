package com.agna.setmaster.ui.base.dialog.module;

import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.dialog.DialogManager;
import com.agna.setmaster.ui.base.dialog.FragmentDialogManager;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class FragmentDialogModule {

    @Provides
    @PerScreen
    DialogManager provideDialogManager(FragmentDialogManager dialogManager){
        return dialogManager;
    }
}
