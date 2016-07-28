package com.agna.setmaster.ui.base.fragment;

import android.support.v4.app.Fragment;

import com.agna.setmaster.ui.base.dialog.DialogManager;
import com.agna.setmaster.ui.base.dialog.FragmentDialogManager;

import dagger.Module;
import dagger.Provides;

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
