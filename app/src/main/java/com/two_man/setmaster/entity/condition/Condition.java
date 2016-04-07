package com.two_man.setmaster.entity.condition;

import java.util.UUID;

/**
 *
 */
public abstract class Condition implements Cloneable {
    protected String id;
    protected boolean active = false;

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
