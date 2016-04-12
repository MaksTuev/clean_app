package com.two_man.setmaster.ui.screen.profile;

import android.content.Context;

import com.two_man.setmaster.ui.base.activity.PerActivity;
import com.two_man.setmaster.ui.screen.profile.setting.changesetting.SettingChangeDialogCreator;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ProfileModule {

    @Provides
    @PerActivity
    SettingChangeDialogCreator provideSettingChangeDialogCreator(Context context){
        return new SettingChangeDialogCreator(context);
    }


}
