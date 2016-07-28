package com.agna.setmaster.ui.screen.profile.setting.change;

import com.agna.setmaster.entity.setting.Setting;

/**
 *
 */
public interface OnSettingChangeListener {
    void onSettingChanged(Setting setting);

    void onSettingDeleted(Setting setting);
}
