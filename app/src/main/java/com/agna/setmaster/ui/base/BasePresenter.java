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
package com.agna.setmaster.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * базовый класс для презентера
 * <p>
 * имеет методы, соответствующие жизненному циклу view
 * при пересоздании View, заново создается и презентер
 *
 * @param <V> тип View
 *            если V не интерфейс, то использование методов View, относящихся к андроид фреймворку запрещено
 */
public class BasePresenter<V> {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    public void onLoad() {
    }

    public void onDestroy() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    public void onRestore(@NonNull Bundle savedInstanceState) {
    }

    public void onSave(@NonNull Bundle outState) {
    }

    protected void addToSubscriptions(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
