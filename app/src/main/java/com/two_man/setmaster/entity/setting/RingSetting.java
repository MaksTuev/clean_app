package com.two_man.setmaster.entity.setting;

import java.util.UUID;

/**
 *
 */
public class RingSetting extends Setting {
    private String id;
    private float value;

    public RingSetting(float value) {
        id = UUID.randomUUID().toString();
        this.value = value;
    }

    private RingSetting(String id, float value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Setting clone() {
        return new RingSetting(id, value);
    }

    public boolean isEnabled(){
        return value != 0;
    }
}
