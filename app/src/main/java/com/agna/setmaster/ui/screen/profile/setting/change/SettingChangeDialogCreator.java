package com.agna.setmaster.ui.screen.profile.setting.change;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.entity.setting.ValuableSetting;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.dialog.BaseDialog;
import com.agna.setmaster.ui.util.ProfileViewUtil;

import javax.inject.Inject;

/**
 *
 */
@PerScreen
public class SettingChangeDialogCreator {
    private Context context;

    @Inject
    public SettingChangeDialogCreator(Context context) {

        this.context = context;
    }

    public BaseDialog createDialog(Profile profile, Setting setting) {
        @ColorInt int accentColor = ProfileViewUtil.getProfileAccentColor(context, profile);
        if (setting instanceof ValuableSetting) {
            return ChangeVolumeSettingDialog.newInstance((ValuableSetting) setting, accentColor);
        } else {
            throw new IllegalArgumentException("Unsupported setting: " + setting.getClass().getSimpleName());
        }
    }
}
