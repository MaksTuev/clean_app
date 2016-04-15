package com.two_man.setmaster.module.profile;

import android.content.Context;

import com.two_man.setmaster.app.PerApplication;
import com.two_man.setmaster.module.condition.ComplexConditionChecker;
import com.two_man.setmaster.module.storage.ProfileStorage;
import com.two_man.setmaster.module.storage.db.DataBaseHelper;
import com.two_man.setmaster.module.storage.db.dao.ProfileDao;
import com.two_man.setmaster.module.storage.db.entity.ProfileObj;

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
                                         ProfileStorage profileStorage,
                                         DefaultProfileCreator defaultProfileCreator){
        return new ProfileService(complexConditionChecker, profileStorage, defaultProfileCreator);
    }

    @Provides
    @PerApplication
    ProfileStorage provideProfileStorage(ProfileDao profileDao){
        return new ProfileStorage(profileDao);
    }

    @Provides
    @PerApplication
    DefaultProfileCreator defaultProfileCreator(Context context){
        return new DefaultProfileCreator(context);
    }

    @Provides
    @PerApplication
    ProfileDao provideProfileDao(DataBaseHelper dataBaseHelper){
        return dataBaseHelper.safeGetDao(ProfileObj.class);
    }
}
