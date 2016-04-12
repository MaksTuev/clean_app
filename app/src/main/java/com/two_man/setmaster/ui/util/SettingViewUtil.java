package com.two_man.setmaster.ui.util;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.setting.MediaVolumeSetting;
import com.two_man.setmaster.entity.setting.RingSetting;
import com.two_man.setmaster.entity.setting.Setting;

/**
 *
 */
public class SettingViewUtil {

    public static int getSettingImage(Class<? extends Setting> settingClass) {
        if(settingClass == RingSetting.class){
            return R.drawable.ic_setting_ring_on;
        } else if(settingClass == MediaVolumeSetting.class){
            return R.drawable.ic_setting_media_volume_on;
        } else{
            throw new IllegalArgumentException("Unsupported setting "+ settingClass.getSimpleName());
        }
    }

    public static int getSettingName(Class<? extends Setting> settingClass) {
        if(settingClass == RingSetting.class){
            return R.string.setting_name_ring;
        } else if(settingClass == MediaVolumeSetting.class){
            return R.string.setting_name_media_volume;
        } else{
            throw new IllegalArgumentException("Unsupported setting "+ settingClass.getSimpleName());
        }
    }
}
