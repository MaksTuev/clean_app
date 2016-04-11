package com.two_man.setmaster.module.profile;

import com.two_man.setmaster.module.condition.ConditionChecker;
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
    ProfileService provideProfileService(ConditionChecker conditionChecker,
                                         ProfileStorage profileStorage){
        return new ProfileService(conditionChecker, profileStorage);
    }

    @Provides
    @PerApplication
    ProfileStorage provideProfileStorage(){
        return new ProfileStorage();
    }
}
