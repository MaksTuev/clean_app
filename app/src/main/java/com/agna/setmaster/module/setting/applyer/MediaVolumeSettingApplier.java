package com.agna.setmaster.module.setting.applyer;

import android.content.Context;
import android.media.AudioManager;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.setting.MediaVolumeSetting;

import javax.inject.Inject;

/**
 *
 */
@PerApplication
public class MediaVolumeSettingApplier implements SettingApplier<MediaVolumeSetting> {
    private Context appContext;
    private AudioManager audioManger;

    @Inject
    public MediaVolumeSettingApplier(Context appContext) {
        this.appContext = appContext;
        audioManger = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void apply(MediaVolumeSetting setting) {
        int maxVolume = audioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volume = (int) (maxVolume * setting.getValue() + 0.49f);
        audioManger.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    @Override
    public MediaVolumeSetting getCurrent() {
        int maxVolume = audioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volume = audioManger.getStreamVolume(AudioManager.STREAM_MUSIC);
        MediaVolumeSetting ringSetting = new MediaVolumeSetting(1.0f * volume / maxVolume);
        return ringSetting;
    }
}
