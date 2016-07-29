package com.agna.setmaster.ui.screen.condition.time;

import com.agna.setmaster.entity.condition.TimeCondition;
import com.agna.setmaster.ui.base.PerScreen;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class TimeConditionModule {
    private TimeCondition timeCondition;

    public TimeConditionModule(TimeCondition timeCondition) {
        this.timeCondition = timeCondition;
    }

    @Provides
    @PerScreen
    TimeCondition provideTimeCondition(){
        return timeCondition;
    }
}
