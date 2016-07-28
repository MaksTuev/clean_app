package com.agna.setmaster.ui.screen.splash;

import android.os.Bundle;

import com.agna.setmaster.R;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.BaseActivityView;

import javax.inject.Inject;

public class SplashActivity extends BaseActivityView {

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void satisfyDependencies() {
        DaggerSplashComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getName() {
        return "Splash";
    }

    @Override
    public BasePresenter getPresenter() {
        return splashPresenter;
    }
}
