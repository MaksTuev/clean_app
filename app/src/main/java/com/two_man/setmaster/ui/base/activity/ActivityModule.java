package com.two_man.setmaster.ui.base.activity;

import android.app.Activity;

import com.two_man.setmaster.ui.base.dialog.ActivityDialogManager;
import com.two_man.setmaster.ui.base.dialog.DialogManager;
import com.two_man.setmaster.ui.navigation.Navigator;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    DialogManager provideDialogManager(ActivityDialogManager dialogManager){
        return dialogManager;
    }

    @Provides
    @PerActivity
    Navigator provideNavigator(Activity activity){
        return new Navigator(activity);
    }

}
