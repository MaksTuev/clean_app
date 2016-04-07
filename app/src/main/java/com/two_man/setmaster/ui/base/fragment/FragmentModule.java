package com.two_man.setmaster.ui.base.fragment;

import android.app.Fragment;

import dagger.Module;
import dagger.Provides;
import ru.litres.android.audio.ui.base.dialog.DialogManager;
import ru.litres.android.audio.ui.base.dialog.FragmentDialogManager;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment provideFragment(){
        return fragment;
    }

    @Provides
    @PerFragment
    DialogManager provideDialogManager(FragmentDialogManager dialogManager){
        return dialogManager;
    }
}
