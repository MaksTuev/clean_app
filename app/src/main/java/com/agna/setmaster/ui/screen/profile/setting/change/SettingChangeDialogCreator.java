/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.ui.screen.profile.setting.change;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.setting.Setting;
import com.agna.setmaster.domain.setting.ValuableSetting;
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
