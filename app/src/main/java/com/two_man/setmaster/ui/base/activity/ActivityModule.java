package com.two_man.setmaster.ui.base.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.two_man.setmaster.ui.base.dialog.ActivityDialogManager;
import com.two_man.setmaster.ui.base.dialog.DialogManager;
import com.two_man.setmaster.ui.navigation.Navigator;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    DialogManager provideDialogManager(ActivityDialogManager dialogManager){
        return dialogManager;
    }

    @Provides
    @PerActivity
    Navigator provideNavigator(AppCompatActivity activity){
        return new Navigator(activity);
    }

}
