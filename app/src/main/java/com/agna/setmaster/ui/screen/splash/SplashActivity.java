/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
