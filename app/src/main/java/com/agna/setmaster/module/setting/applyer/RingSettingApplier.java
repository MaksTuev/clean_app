package com.agna.setmaster.module.setting.applyer;

import android.content.Context;
import android.media.AudioManager;

import com.agna.setmaster.entity.setting.RingSetting;

/**
 *
 */
public class RingSettingApplier implements SettingApplier<RingSetting> {
    private Context appContext;
    private AudioManager audioManger;

    public RingSettingApplier(Context appContext) {
        this.appContext = appContext;
        audioManger = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void apply(RingSetting setting) {
        int maxVolume = audioManger.getStreamMaxVolume(AudioManager.STREAM_RING);
        int volume = (int) (maxVolume * setting.getValue() + 0.49f);
        audioManger.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
    }

    @Override
    public RingSetting getCurrent() {
        int maxVolume = audioManger.getStreamMaxVolume(AudioManager.STREAM_RING);
        int volume = audioManger.getStreamVolume(AudioManager.STREAM_RING);
        RingSetting ringSetting = new RingSetting(1.0f * volume / maxVolume);
        return ringSetting;
    }
}
