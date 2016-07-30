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
package com.agna.setmaster.ui.util;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.setting.MediaVolumeSetting;
import com.agna.setmaster.domain.setting.RingSetting;
import com.agna.setmaster.domain.setting.Setting;

/**
 *
 */
public class SettingViewUtil {

    public static int getSettingImage(Class<? extends Setting> settingClass) {
        if (settingClass == RingSetting.class) {
            return R.drawable.ic_setting_ring_on;
        } else if (settingClass == MediaVolumeSetting.class) {
            return R.drawable.ic_setting_media_volume_on;
        } else {
            throw new IllegalArgumentException("Unsupported setting " + settingClass.getSimpleName());
        }
    }

    public static int getSettingName(Class<? extends Setting> settingClass) {
        if (settingClass == RingSetting.class) {
            return R.string.setting_name_ring;
        } else if (settingClass == MediaVolumeSetting.class) {
            return R.string.setting_name_media_volume;
        } else {
            throw new IllegalArgumentException("Unsupported setting " + settingClass.getSimpleName());
        }
    }
}
