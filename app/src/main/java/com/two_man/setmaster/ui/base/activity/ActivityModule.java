package com.two_man.setmaster.ui.base.activity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.litres.android.audio.ui.base.dialog.ActivityDialogManager;
import ru.litres.android.audio.ui.base.dialog.DialogManager;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    DialogManager provideDialogManager(ActivityDialogManager dialogManager){
        return dialogManager;
    }

}
