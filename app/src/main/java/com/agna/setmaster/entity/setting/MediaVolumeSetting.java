package com.agna.setmaster.entity.setting;

/**
 *
 */
public class MediaVolumeSetting extends ValuableSetting {

    public MediaVolumeSetting() {
        super(0);
    }

    public MediaVolumeSetting(float value) {
        super(value);
    }

    private MediaVolumeSetting(String id, float value) {
        super(id, value);
    }

    @Override
    public Setting clone() {
        return new MediaVolumeSetting(getId(), getValue());
    }

    public boolean isEnabled() {
        return getValue() != 0;
    }
}
