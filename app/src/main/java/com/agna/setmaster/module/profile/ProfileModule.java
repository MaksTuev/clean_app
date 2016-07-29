package com.agna.setmaster.module.profile;

import com.agna.setmaster.app.PerApplication;
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
    ProfileDao provideProfileDao(DataBaseHelper dataBaseHelper) {
        return dataBaseHelper.safeGetDao(ProfileObj.class);
    }
}
