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

import com.agna.setmaster.interactor.initialize.InitializeAppInteractor;
import com.agna.setmaster.interactor.scheduler.SchedulersProvider;
import com.agna.setmaster.interactor.service.AppServiceInteractor;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.common.navigation.Navigator;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

/**
 * presenter экрана сплеша
 */
@PerScreen
public class SplashPresenter extends BasePresenter<SplashActivity> {

    private InitializeAppInteractor initializeAppInteractor;
    private AppServiceInteractor appServiceInteractor;
    private Navigator navigator;
    private final SchedulersProvider schedulersProvider;

    @Inject
    public SplashPresenter(InitializeAppInteractor initializeAppInteractor,
                           AppServiceInteractor appServiceInteractor,
                           Navigator navigator,
                           SchedulersProvider schedulersProvider) {
        this.initializeAppInteractor = initializeAppInteractor;
        this.appServiceInteractor = appServiceInteractor;
        this.navigator = navigator;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        appServiceInteractor.start();
        Observable.zip(
                initializeAppInteractor.initialize(),
                Observable.timer(500, TimeUnit.MILLISECONDS),
                (o1, o2) -> null)
                .observeOn(schedulersProvider.main())
                .subscribe(this::onInitialized);
    }

    private void onInitialized(Object o) {
        navigator.openMain();
    }
}
