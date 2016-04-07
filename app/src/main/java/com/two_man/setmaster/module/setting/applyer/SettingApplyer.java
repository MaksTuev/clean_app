package com.two_man.setmaster.module.setting.applyer;

import com.two_man.setmaster.entity.setting.Setting;

/**
 *
 */
public interface SettingApplyer<S extends Setting> {
    void apply(S setting);
}
