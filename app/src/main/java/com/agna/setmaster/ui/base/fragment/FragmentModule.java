package com.agna.setmaster.ui.base.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.agna.setmaster.ui.base.PerScreen;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerScreen
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @PerScreen
    AppCompatActivity provideActivity() {
        return (AppCompatActivity) fragment.getActivity();
    }

}
