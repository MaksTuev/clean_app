package com.two_man.setmaster.module.condition.simple.wifi;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.two_man.setmaster.entity.condition.WiFiCondition;
import com.two_man.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.two_man.setmaster.module.condition.simple.ConditionWrapper;
import com.two_man.setmaster.module.condition.simple.SimpleConditionChecker;
import com.two_man.setmaster.util.rx.SimpleOnSubscribe;

import java.util.ArrayList;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 */
public class WifiConditionChecker implements SimpleConditionChecker<WiFiCondition> {

    private Context appContext;
    private WifiManager wifiManager;
    private ArrayList<ConditionWrapper<WiFiCondition>> conditions = new ArrayList<>();
    private SimpleOnSubscribe<ConditionStateChangedEvent> onConditionChangedOnSubscribe = new SimpleOnSubscribe<>();

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
