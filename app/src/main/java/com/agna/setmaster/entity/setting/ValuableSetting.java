package com.agna.setmaster.entity.setting;

/**
 *
 */
public abstract class ValuableSetting extends Setting {
    private float value;


    public ValuableSetting(float value) {
        this.value = value;
    }

    public ValuableSetting(String id, float value) {
        super(id);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
