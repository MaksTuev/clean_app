package com.agna.setmaster.entity.condition;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public abstract class Condition implements Cloneable, Serializable {
    private String id;
    private boolean active = false;

    public Condition(String id, boolean active) {
        this.id = id;
        this.active = active;
    }

    @Override
    public abstract Condition clone();

    public Condition() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
