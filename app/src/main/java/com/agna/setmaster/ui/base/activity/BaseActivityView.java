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
package com.agna.setmaster.ui.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;

import com.agna.setmaster.ui.base.BaseView;
import com.agna.setmaster.ui.base.HasName;
import com.agna.setmaster.app.log.RemoteLogger;

/**
 * базовый класс для вью, основанной на Activity
 */
public abstract class BaseActivityView extends BaseActivity implements BaseView, HasName {


    private Handler handler = new Handler();

    /**
     * в реализации этого метода необходимо удовлетворить зависимости
     */
    protected abstract void satisfyDependencies();

    @LayoutRes
    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RemoteLogger.logViewCreated(this);
        setContentView(getContentView());
        satisfyDependencies();
        initPresenter();
        if (savedInstanceState != null) {
            handler.post(() -> getPresenter().onRestore(savedInstanceState));
        }
        handler.post(() -> getPresenter().onLoad());
    }

    @Override
    @CallSuper
    public void initPresenter() {
        getPresenter().attachView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPresenter().onSave(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void goBack() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        RemoteLogger.logViewDestroyed(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getPresenter().onActivityResult(requestCode, resultCode, data);
    }
}
