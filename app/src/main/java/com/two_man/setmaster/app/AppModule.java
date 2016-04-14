package com.two_man.setmaster.app;

import android.content.Context;

import com.two_man.setmaster.interactor.InteractorModule;
import com.two_man.setmaster.module.condition.ConditionModule;
import com.two_man.setmaster.module.profile.ProfileModule;
import com.two_man.setmaster.module.setting.SettingModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ConditionModule.class,
        SettingModule.class,
        ProfileModule.class,
        InteractorModule.class})
public class AppModule {
        Context appContext;

        public AppModule(Context appContext) {
                this.appContext = appContext;
        }

        @PerApplication
        @Provides
        Context provideContext(){
                return appContext;
        }
}
