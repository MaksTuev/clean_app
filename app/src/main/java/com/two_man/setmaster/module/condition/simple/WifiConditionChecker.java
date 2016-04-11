package com.two_man.setmaster.module.condition.simple;


import com.two_man.setmaster.entity.condition.WiFiCondition;

import rx.Observable;

/**
 *
 */
public class WifiConditionChecker implements SimpleConditionChecker<WiFiCondition> {
    @Override
    public void unregister(ConditionWrapper<WiFiCondition> condition) {

    }

    @Override
    public void register(ConditionWrapper<WiFiCondition> condition) {

    }

    @Override
    public Observable<ConditionStateChangedEvent> observeConditionStateChanged() {
        return null;
    }
}
