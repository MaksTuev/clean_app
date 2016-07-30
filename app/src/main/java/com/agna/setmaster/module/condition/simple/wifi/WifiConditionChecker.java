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
package com.agna.setmaster.module.condition.simple.wifi;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.condition.WiFiCondition;
import com.agna.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.agna.setmaster.module.condition.simple.ConditionWrapper;
import com.agna.setmaster.module.condition.simple.SimpleConditionChecker;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 *
 */
@PerApplication
public class WifiConditionChecker implements SimpleConditionChecker<WiFiCondition> {

    private Context appContext;
    private WifiManager wifiManager;
    private ArrayList<ConditionWrapper<WiFiCondition>> conditions = new ArrayList<>();
    private PublishSubject<ConditionStateChangedEvent> conditionChangedSubject = PublishSubject.create();

    @Inject
    public WifiConditionChecker(Context appContext) {
        this.appContext = appContext;
        wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void unregister(ConditionWrapper<WiFiCondition> conditionWrapper) {
        synchronized (this) {
            for (ConditionWrapper innerConditionWrapper : conditions) {
                if (innerConditionWrapper.getCondition().getId().equals(conditionWrapper.getCondition().getId())) {
                    conditions.remove(innerConditionWrapper);
                    updateListeners(innerConditionWrapper, false);
                    return;
                }
            }
        }
    }

    @Override
    public void register(ConditionWrapper<WiFiCondition> condition) {
        synchronized (this) {
            conditions.add(condition);
            checkCondition(condition);
        }
    }

    @Override
    public Observable<ConditionStateChangedEvent> observeConditionStateChanged() {
        return conditionChangedSubject;
    }

    public void onConnected() {
        synchronized (this) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            for (ConditionWrapper<WiFiCondition> conditionWrapper : conditions) {
                if (conditionWrapper.getCondition().getNetworkName().equals(ssid)) {
                    conditionWrapper.getCondition().setActive(true);
                    updateListeners(conditionWrapper, true);
                }
            }
        }
    }

    public void onDisconnected() {
        synchronized (this) {
            for (ConditionWrapper<WiFiCondition> conditionWrapper : conditions) {
                if (conditionWrapper.getCondition().isActive()) {
                    conditionWrapper.getCondition().setActive(false);
                    updateListeners(conditionWrapper, false);
                }
            }
        }
    }

    private void checkCondition(ConditionWrapper<WiFiCondition> conditionWrapper) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        if (conditionWrapper.getCondition().getNetworkName().equals(ssid)) {
            conditionWrapper.getCondition().setActive(true);
            updateListeners(conditionWrapper, true);
        }
    }

    private void updateListeners(ConditionWrapper conditionWrapper, boolean active) {
        ConditionStateChangedEvent event = new ConditionStateChangedEvent(
                conditionWrapper.getProfileId(), conditionWrapper.getCondition().getId(), active);
        conditionChangedSubject.onNext(event);
    }
}
