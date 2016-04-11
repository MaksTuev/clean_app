package com.two_man.setmaster.ui.screen.splash;

import com.two_man.setmaster.ui.app.AppComponent;
import com.two_man.setmaster.ui.base.activity.ActivityModule;
import com.two_man.setmaster.ui.base.activity.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface SplashComponent {
    void inject(SplashActivity fragment);
}
