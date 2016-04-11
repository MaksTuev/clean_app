package com.two_man.setmaster.module.condition.simple;

import com.two_man.setmaster.entity.condition.Condition;

import rx.Observable;

/**
 *
 */
public interface  SimpleConditionChecker <C extends Condition> {

    void unregister(ConditionWrapper<C> condition);

    void register(ConditionWrapper<C> condition);

    Observable<ConditionStateChangedEvent> observeConditionStateChanged();
}
