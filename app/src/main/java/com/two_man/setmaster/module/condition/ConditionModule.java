package com.two_man.setmaster.module.condition;

import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.condition.WiFiCondition;
import com.two_man.setmaster.module.condition.simple.SimpleConditionChecker;
import com.two_man.setmaster.module.condition.simple.WifiConditionChecker;
import com.two_man.setmaster.ui.app.PerApplication;

import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ConditionModule {

    @Provides
    @PerApplication
    ConditionChecker provideConditionChecker(
            Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers){
        return new ConditionChecker(simpleConditionCheckers);
    }

    @Provides
    @PerApplication
    Map<Class<? extends Condition>, SimpleConditionChecker<?>> provideSimpleConditionCheckers(){
        Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers = new HashMap<>();
        simpleConditionCheckers.put(WiFiCondition.class, new WifiConditionChecker());
        return simpleConditionCheckers;
    }
}
