package com.agna.setmaster.entity.setting;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public abstract class Setting implements Cloneable, Serializable {

    private String id;

    public Setting() {
        id = UUID.randomUUID().toString();
    }


    public Setting(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public abstract Setting clone();
}
