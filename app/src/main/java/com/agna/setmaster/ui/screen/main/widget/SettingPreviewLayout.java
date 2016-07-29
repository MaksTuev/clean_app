package com.agna.setmaster.ui.screen.main.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.setting.MediaVolumeSetting;
import com.agna.setmaster.entity.setting.RingSetting;
import com.agna.setmaster.entity.setting.Setting;

import java.util.ArrayList;

/**
 *
 */
public class SettingPreviewLayout extends LinearLayout {
    public SettingPreviewLayout(Context context) {
        super(context);
        initView(context);
    }

    public SettingPreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SettingPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SettingPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    protected void initView(Context context) {
        inflate(context);
    }

    public void showSettings(ArrayList<Setting> settings) {
        removeAllViews();
        for (Setting setting : settings) {
            View settingView = LayoutInflater.from(getContext()).inflate(R.layout.setting_preview_layout_item, this, false);
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            this.addView(settingView, layoutParams);
            ImageView settingIcon = (ImageView) settingView.findViewById(R.id.icon);
            settingIcon.setImageResource(getSettingImage(setting));
        }
    }

    @DrawableRes
    private int getSettingImage(Setting setting) {
        if (setting instanceof RingSetting) {
            RingSetting ringSetting = (RingSetting) setting;
            return ringSetting.isEnabled()
                    ? R.drawable.ic_setting_ring_on
                    : R.drawable.ic_setting_ring_off;
        } else if (setting instanceof MediaVolumeSetting) {
            MediaVolumeSetting ringSetting = (MediaVolumeSetting) setting;
            return ringSetting.isEnabled()
                    ? R.drawable.ic_setting_media_volume_on
                    : R.drawable.ic_setting_media_volume_off;
        } else {
            throw new IllegalArgumentException("Setting " + setting.getClass().getSimpleName() + "not supported");
        }

    }

    private void inflate(Context context) {
        setOrientation(HORIZONTAL);
    }


}
