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
package com.agna.setmaster.interactor.setting.applyer;

import android.content.Context;
import android.media.AudioManager;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.domain.setting.MediaVolumeSetting;

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
