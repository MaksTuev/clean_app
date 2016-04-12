package com.two_man.setmaster.ui.util;

import com.two_man.setmaster.entity.setting.Setting;

/**
 *
 */
public class SettingUtil {
    public static Setting newInstance(Class<? extends Setting> settingClass){
        try{
            return settingClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
