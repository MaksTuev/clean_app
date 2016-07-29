package com.agna.setmaster.ui.base.activity;

import android.support.v7.app.AppCompatActivity;

import com.agna.setmaster.ui.base.PerScreen;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerScreen
    AppCompatActivity provideActivity() {
        return activity;
    }
}
