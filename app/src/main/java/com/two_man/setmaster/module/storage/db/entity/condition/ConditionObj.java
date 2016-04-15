package com.two_man.setmaster.module.storage.db.entity.condition;


/**
 *
 */
public abstract class ConditionObj{
    private String id;

    public ConditionObj(String id) {
        this.id = id;
    }


    public ConditionObj() {

    }

    public String getId() {
        return id;
    }

}
