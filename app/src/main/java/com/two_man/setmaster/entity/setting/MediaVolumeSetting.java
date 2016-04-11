package com.two_man.setmaster.entity.setting;

import java.util.UUID;

/**
 *
 */
public class MediaVolumeSetting extends Setting {
    private String id;
    private float value;

    public MediaVolumeSetting(float value) {
        id = UUID.randomUUID().toString();
        this.value = value;
    }

    private MediaVolumeSetting(String id, float value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Setting clone() {
        return new MediaVolumeSetting(id, value);
    }

    public boolean isEnabled(){
        return value != 0;
    }
}
