package com.agna.setmaster.module.storage.db.entity;

import com.agna.setmaster.entity.ConditionSet;
import com.agna.setmaster.entity.condition.Condition;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class ConditionSetObj implements Cloneable, Serializable{
    private ArrayList<Condition> conditions = new ArrayList<>();
    private String id;

    public ConditionSetObj() {
            }

    private ConditionSetObj(ConditionSet conditionSet) {
        this.id = id;
        this.conditions = conditions;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

}
