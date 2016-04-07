package com.two_man.setmaster.entity;

import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.util.CloneUtil;

import java.util.ArrayList;

/**
 *
 */
public class ConditionSet implements Cloneable{
    private ArrayList<Condition> conditions;
    private boolean active;

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    private ConditionSet(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public ConditionSet clone(){
        return new ConditionSet(CloneUtil.cloneConditionList(conditions));
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
