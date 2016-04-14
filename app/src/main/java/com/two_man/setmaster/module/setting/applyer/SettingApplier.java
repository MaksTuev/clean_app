package com.two_man.setmaster.module.setting.applyer;

import com.two_man.setmaster.entity.setting.Setting;

/**
 *
 */
public interface SettingApplier<S extends Setting> {
    void apply(S setting);
    S getCurrent();
}
