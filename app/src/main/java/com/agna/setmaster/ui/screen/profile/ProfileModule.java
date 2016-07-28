package com.agna.setmaster.ui.screen.profile;

import android.content.Context;

import com.agna.setmaster.ui.base.activity.PerActivity;
import com.agna.setmaster.ui.screen.profile.setting.change.SettingChangeDialogCreator;

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
