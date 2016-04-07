package com.two_man.setmaster.module.condition.simple;

import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.module.condition.ConditionWrapper;

import rx.Observable;

/**
 *
 */
public interface  SimpleConditionChecker <C extends Condition> {

    void unregister(ConditionWrapper condition);

    void register(ConditionWrapper condition);

    Observable<ConditionStateChangedEvent> observeConditionStateChanged();
}
