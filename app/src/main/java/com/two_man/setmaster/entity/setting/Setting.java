package com.two_man.setmaster.entity.setting;

import java.io.Serializable;

/**
 *
 */
public abstract class Setting implements Cloneable, Serializable {
    @Override
    public abstract Setting clone();
}
