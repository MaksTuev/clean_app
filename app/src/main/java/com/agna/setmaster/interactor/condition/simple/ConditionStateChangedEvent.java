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
package com.agna.setmaster.interactor.condition.simple;

/**
 *
 */
public class ConditionStateChangedEvent {
    private String profileId;
    private String conditionId;
    private boolean active;

    public ConditionStateChangedEvent(String profileId, String conditionId, boolean active) {
        this.profileId = profileId;
        this.conditionId = conditionId;
        this.active = active;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getConditionId() {
        return conditionId;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "ConditionStateChangedEvent{" +
                "profileId='" + profileId + '\'' +
                ", conditionId='" + conditionId + '\'' +
                ", active=" + active +
                '}';
    }
}
