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
package com.agna.setmaster.ui.screen.main.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.util.ProfileIconHelper;
import com.agna.setmaster.ui.util.ProfileViewUtil;

/**
 *
 */
public class ProfileListItemView extends RelativeLayout {
    private ImageView profileIcon;
    private View profileActiveIndicator;
    private TextView profileNameText;
    private TextView profileStatusText;
    private SettingPreviewLayout profileSettingPreviewLayout;

    private Profile profile;
    private OnProfileActionListener onProfileActionListener;

    public ProfileListItemView(Context context) {
        super(context);
        initView(context);
    }

    public ProfileListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProfileListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ProfileListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    protected void initView(Context context) {
        inflate(context);
        findChildViews(context);
        initListeners();
    }

    private void initListeners() {
        this.setOnClickListener(v -> onProfileActionListener.onProfileClick(profile));
    }

    public void show(Profile profile) {
        this.profile = profile;
        profileIcon.setImageResource(ProfileIconHelper.getIconRes(profile.getIconId()));
        profileActiveIndicator.setVisibility(profile.isActive() ? VISIBLE : GONE);
        profileNameText.setText(profile.getName());
        profileStatusText.setText(ProfileViewUtil.getProfileStatusText(profile));
        profileSettingPreviewLayout.showSettings(profile.getSettings());
    }

    public void setListener(OnProfileActionListener onProfileActionListener) {
        this.onProfileActionListener = onProfileActionListener;
    }

    private void findChildViews(Context context) {
        profileIcon = (ImageView) findViewById(R.id.profile_icon);
        profileActiveIndicator = findViewById(R.id.profile_active_indicator);
        profileNameText = (TextView) findViewById(R.id.profile_name_text);
        profileStatusText = (TextView) findViewById(R.id.profile_status_text);
        profileSettingPreviewLayout = (SettingPreviewLayout) findViewById(R.id.profile_setting_preview);
    }


    private void inflate(Context context) {
        inflate(context, R.layout.profile_list_item, this);
    }

    public interface OnProfileActionListener {
        void onProfileClick(Profile profile);
    }
}
