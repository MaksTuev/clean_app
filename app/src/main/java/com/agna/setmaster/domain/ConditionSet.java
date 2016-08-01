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
package com.agna.setmaster.domain;

import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.util.CloneUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Set of conditions
 */
public class ConditionSet implements Cloneable, Serializable {
    private ArrayList<Condition> conditions = new ArrayList<>();
    private boolean active;
    private String id;

    public ConditionSet() {
        id = UUID.randomUUID().toString();
    }

    private ConditionSet(String id, ArrayList<Condition> conditions, boolean active) {
        this.id = id;
        this.conditions = conditions;
        this.active = active;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public ConditionSet clone() {
        return new ConditionSet(id, CloneUtil.cloneConditionList(conditions), active);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public void delete(Condition conditionForDelete) {
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);
            if (condition.getId().equals(conditionForDelete.getId())) {
                conditions.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Condition " + conditionForDelete + " not exist");
    }

    public boolean isEmpty() {
        return conditions.size() == 0;
    }
}
