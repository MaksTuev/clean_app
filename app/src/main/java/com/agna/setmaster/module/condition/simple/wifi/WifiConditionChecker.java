package com.agna.setmaster.module.condition.simple.wifi;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.condition.WiFiCondition;
import com.agna.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.agna.setmaster.module.condition.simple.ConditionWrapper;
import com.agna.setmaster.module.condition.simple.SimpleConditionChecker;
import com.agna.setmaster.util.rx.SimpleOnSubscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 */
@PerApplication
public class WifiConditionChecker implements SimpleConditionChecker<WiFiCondition> {

    private Context appContext;
    private WifiManager wifiManager;
    private ArrayList<ConditionWrapper<WiFiCondition>> conditions = new ArrayList<>();
    private SimpleOnSubscribe<ConditionStateChangedEvent> onConditionChangedOnSubscribe = new SimpleOnSubscribe<>();

    @Inject
    public WifiConditionChecker(Context appContext) {
        this.appContext = appContext;
        wifiManager = (WifiManager)appContext.getSystemService(Context.WIFI_SERVICE);
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
        return Observable.create(onConditionChangedOnSubscribe)
                .subscribeOn(Schedulers.io());
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
        if(conditionWrapper.getCondition().getNetworkName().equals(ssid)){
            conditionWrapper.getCondition().setActive(true);
            updateListeners(conditionWrapper, true);
        }
    }

    private void updateListeners(ConditionWrapper conditionWrapper, boolean active) {
        ConditionStateChangedEvent event = new ConditionStateChangedEvent(
                conditionWrapper.getProfileId(), conditionWrapper.getCondition().getId(), active);
        onConditionChangedOnSubscribe.emit(event);
    }
}
