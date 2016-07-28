package com.agna.setmaster.entity;

import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.util.CloneUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 */
public class ConditionSet implements Cloneable, Serializable{
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
    public ConditionSet clone(){
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
        for(int i = 0; i<conditions.size(); i++){
            Condition condition = conditions.get(i);
            if(condition.getId().equals(conditionForDelete.getId())){
                conditions.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Condition "+conditionForDelete+" not exist");
    }

    public boolean isEmpty() {
        return conditions.size() == 0;
    }
}
