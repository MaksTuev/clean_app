/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.interactor.condition;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.domain.condition.TimeCondition;
import com.agna.setmaster.domain.condition.WiFiCondition;
import com.agna.setmaster.interactor.condition.simple.SimpleConditionChecker;
import com.agna.setmaster.interactor.condition.simple.time.TimeConditionChecker;
import com.agna.setmaster.interactor.condition.simple.wifi.WifiConditionChecker;

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
    Map<Class<? extends Condition>, SimpleConditionChecker<?>> provideSimpleConditionCheckers(
            WifiConditionChecker wifiConditionChecker,
            TimeConditionChecker timeConditionChecker) {
        Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers = new HashMap<>();
        simpleConditionCheckers.put(WiFiCondition.class, wifiConditionChecker);
        simpleConditionCheckers.put(TimeCondition.class, timeConditionChecker);
        return simpleConditionCheckers;
    }

    @Provides
    @PerApplication
    ArrayList<Class<? extends Condition>> provideSupportedConditions() {
        ArrayList<Class<? extends Condition>> supportedConditions = new ArrayList<>();
        supportedConditions.add(TimeCondition.class);
        supportedConditions.add(WiFiCondition.class);
        return supportedConditions;
    }
}
