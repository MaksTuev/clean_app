package com.two_man.setmaster.module.condition;

import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.two_man.setmaster.module.condition.simple.ConditionWrapper;
import com.two_man.setmaster.module.condition.simple.SimpleConditionChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import rx.Observable;
import timber.log.Timber;

/**
 *
 */
public class ComplexConditionChecker {

    private Map<Class<? extends Condition>, SimpleConditionChecker<?>> simpleConditionCheckers;
    private Observable<ConditionStateChangedEvent> conditionStateChangedObservable;

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