package com.agna.setmaster.module.profile;

import android.content.Context;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.module.condition.ComplexConditionChecker;
import com.agna.setmaster.module.storage.ProfileStorage;
import com.agna.setmaster.module.storage.db.DataBaseHelper;
import com.agna.setmaster.module.storage.db.dao.ProfileDao;
import com.agna.setmaster.module.storage.db.entity.ProfileObj;

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
