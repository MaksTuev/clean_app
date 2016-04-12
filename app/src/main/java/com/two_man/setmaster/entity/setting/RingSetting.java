package com.two_man.setmaster.entity.setting;

/**
 *
 */
public class RingSetting extends ValuableSetting {

    public RingSetting() {
        super(0);
    }

    public RingSetting(float value) {
        super(value);
    }

    private RingSetting(String id, float value) {
        super(id, value);
    }

    @Override
    public Setting clone() {
        return new RingSetting(getId(), getValue());
    }

    public boolean isEnabled(){
        return getValue() != 0;
    }
}
