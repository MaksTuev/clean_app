package com.agna.setmaster.ui.screen.splash;

import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface SplashComponent {
    void inject(SplashActivity fragment);
}
