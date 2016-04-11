package com.two_man.setmaster.ui.base.fragment;

import android.app.Activity;
import android.app.Fragment;

import com.two_man.setmaster.ui.base.dialog.DialogManager;
import com.two_man.setmaster.ui.base.dialog.FragmentDialogManager;
import com.two_man.setmaster.ui.navigation.Navigator;

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
