package com.two_man.setmaster.module.condition.simple;

import com.two_man.setmaster.entity.condition.TimeCondition;

import rx.Observable;

/**
 *
 */
public class TimeConditionChecker implements SimpleConditionChecker<TimeCondition> {
    @Override
    public void unregister(ConditionWrapper<TimeCondition> condition) {

    }

    @Override
    public void register(ConditionWrapper<TimeCondition> condition) {

    }

    @Override
    public Observable<ConditionStateChangedEvent> observeConditionStateChanged() {
        return Observable.empty();
    }
}
