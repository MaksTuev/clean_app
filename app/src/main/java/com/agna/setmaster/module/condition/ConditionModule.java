package com.agna.setmaster.module.condition;

import android.content.Context;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.condition.TimeCondition;
import com.agna.setmaster.entity.condition.WiFiCondition;
import com.agna.setmaster.module.condition.simple.SimpleConditionChecker;
import com.agna.setmaster.module.condition.simple.time.TimeConditionChecker;
import com.agna.setmaster.module.condition.simple.wifi.WifiConditionChecker;

import java.util.ArrayList;
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
    ComplexConditionChecker provideConditionChecker(
            Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers){
        return new ComplexConditionChecker(simpleConditionCheckers);
    }

    @Provides
    @PerApplication
    Map<Class<? extends Condition>, SimpleConditionChecker<?>> provideSimpleConditionCheckers(
            WifiConditionChecker wifiConditionChecker,
            TimeConditionChecker timeConditionChecker){
        Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers = new HashMap<>();
        simpleConditionCheckers.put(WiFiCondition.class, wifiConditionChecker);
        simpleConditionCheckers.put(TimeCondition.class, timeConditionChecker);
        return simpleConditionCheckers;
    }

    @Provides
    @PerApplication
    ArrayList<Class<? extends Condition>> provideSupportedConditions(){
        ArrayList<Class<? extends Condition>> supportedConditions = new ArrayList<>();
        supportedConditions.add(TimeCondition.class);
        supportedConditions.add(WiFiCondition.class);
        return supportedConditions;
    }

    @Provides
    @PerApplication
    TimeConditionChecker provideTimeConditionChecker(Context appContext){
        return new TimeConditionChecker(appContext);
    }

    @Provides
    @PerApplication
    WifiConditionChecker provideWifiConditionChecker(Context context){
        return new WifiConditionChecker(context);
    }

}
