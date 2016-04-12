package com.two_man.setmaster.ui.screen.profile.setting.changesetting;

import com.two_man.setmaster.entity.setting.Setting;

/**
 *
 */
public interface OnSettingChangeListener {
    void onSettingChanged(Setting setting);

    void onSettingDeleted(Setting setting);
}
