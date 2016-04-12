package com.two_man.setmaster.ui.screen.profile.condition.holder;

import com.two_man.setmaster.entity.condition.Condition;

/**
 *
 */
public interface ConditionHolder<C extends Condition> {
    void bind(C condition);
}
