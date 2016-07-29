package com.agna.setmaster.module.setting.applyer;

import com.agna.setmaster.entity.setting.Setting;

/**
 *
 */
public interface SettingApplier<S extends Setting> {
    void apply(S setting);

    S getCurrent();
}
