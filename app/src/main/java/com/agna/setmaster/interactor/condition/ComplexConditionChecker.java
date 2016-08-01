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
import com.agna.setmaster.domain.ConditionSet;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.interactor.condition.simple.ConditionStateChangedEvent;
import com.agna.setmaster.interactor.condition.simple.ConditionWrapper;
import com.agna.setmaster.interactor.condition.simple.SimpleConditionChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import rx.Observable;
import timber.log.Timber;

/**
 * Responsible for checking conditions
 */
@PerApplication
public class ComplexConditionChecker {

    private Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers;
    private Observable<ConditionStateChangedEvent> conditionStateChangedObservable;

    @Inject
    public ComplexConditionChecker(Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckersMap) {
        this.simpleConditionCheckers = simpleConditionCheckersMap;
        conditionStateChangedObservable = Observable.merge(
                StreamSupport.stream(this.simpleConditionCheckers.values())
                        .map(SimpleConditionChecker::observeConditionStateChanged)
                        .collect(Collectors.toList()))
                .doOnNext(event -> Timber.d("ConditionChanged: " + event));
    }

    public void updateConditionRegistrations(Profile oldProfile, Profile newProfile) {
        String profileId = oldProfile == null ? newProfile.getId() : oldProfile.getId();
        List<Condition> oldConditions = getAllConditions(oldProfile);
        List<Condition> newConditions = getAllConditions(newProfile);
        unregisterConditions(oldConditions, profileId);
        registerConditions(newConditions, profileId);
    }

    public Observable<ConditionStateChangedEvent> observeConditionStateChanged() {
        return conditionStateChangedObservable;
    }

    private void unregisterConditions(List<Condition> conditions, String profileId) {
        for (Condition condition : conditions) {
            simpleConditionCheckers.get(condition.getClass())
                    .unregister(new ConditionWrapper(condition, profileId));
        }
    }

    private void registerConditions(List<Condition> conditions, String profileId) {
        for (Condition condition : conditions) {
            simpleConditionCheckers.get(condition.getClass())
                    .register(new ConditionWrapper(condition, profileId));
        }
    }

    private List<Condition> getAllConditions(Profile profile) {
        List<Condition> result = new ArrayList<>();
        if (profile == null) {
            return result;
        }
        for (ConditionSet conditionSet : profile.getConditionSets()) {
            result.addAll(conditionSet.getConditions());
        }
        return result;
    }

}
