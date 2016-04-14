package com.two_man.setmaster.module.profile;

import com.two_man.setmaster.module.condition.ComplexConditionChecker;
import com.two_man.setmaster.ui.app.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ProfileModule {

    @Provides
    @PerApplication
    ProfileService provideProfileService(ComplexConditionChecker complexConditionChecker,
                                         ProfileStorage profileStorage){
        return new ProfileService(complexConditionChecker, profileStorage);
    }

    @Provides
    @PerApplication
    ProfileStorage provideProfileStorage(){
        return new ProfileStorage();
    }
}
