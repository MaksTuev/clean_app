package com.agna.setmaster.ui.screen.profile.condition.holder;

import com.agna.setmaster.entity.condition.Condition;

/**
 *
 */
public interface ConditionHolder<C extends Condition> {
    void bind(C condition);
}
