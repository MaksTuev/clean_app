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
package com.agna.setmaster.ui.screen.editprofile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.BaseActivityView;
import com.agna.setmaster.ui.util.ProfileViewUtil;
import com.agna.setmaster.ui.util.StatusBarUtil;
import com.agna.setmaster.util.ProfileIconHelper;

import javax.inject.Inject;

/**
 *
 */
public class EditProfileActivity extends BaseActivityView {

    public static final String EXTRA_PROFILE = "EXTRA_PROFILE";
    @Inject
    EditProfilePresenter presenter;

    private View backBtn;
    private View saveBtn;
    private EditText nameEt;
    private RecyclerView iconGrid;
    private IconGridAdapter iconAdapter;
    private ImageView icon;
    private View contentContainer;

    private Handler handler = new Handler();

    @Override
    protected void satisfyDependencies() {
        Profile profile = (Profile) getIntent().getSerializableExtra(EXTRA_PROFILE);
        DaggerEditProfileComponent.builder()
                .activityModule(new ActivityModule(this))
                .editProfileScreenModule(new EditProfileScreenModule(profile))
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initGrid();
        initListeners();
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
    }

    public void bindProfile(Profile profile) {
        if (profile != null) {
            nameEt.setText(profile.getName());
            iconAdapter.setSelectedIcon(profile.getIconId());
        } else {
            handler.post(() -> iconAdapter.setSelectedIcon(0));
        }
        StatusBarUtil.changeColor(this, profile != null && profile.isActive());
        iconAdapter.setSelectedIconColor(ProfileViewUtil.getProfileAccentColor(this, profile));
        contentContainer.setBackgroundColor(ProfileViewUtil.getProfileAccentColor(this, profile));

    }

    private void findViews() {
        contentContainer = findViewById(R.id.edit_profile_content_container);
        backBtn = findViewById(R.id.edit_profile_decline_btn);
        saveBtn = findViewById(R.id.edit_profile_save_btn);
        nameEt = (EditText) findViewById(R.id.edit_profile_name_et);
        iconGrid = (RecyclerView) findViewById(R.id.edit_profile_icons_grid);
        icon = (ImageView) findViewById(R.id.edit_profile_icon);
        icon.setColorFilter(ContextCompat.getColor(this, R.color.profile_icon_half_transparent), PorterDuff.Mode.DST_IN);
    }

    private void initListeners() {
        backBtn.setOnClickListener(v -> onBackPressed());
        saveBtn.setOnClickListener(v -> presenter.saveProfile(
                nameEt.getText().toString(),
                iconAdapter.getSelectedIcon()));
    }

    private void initGrid() {
        iconAdapter = new IconGridAdapter(iconGrid, ProfileIconHelper.getAll(), this::updateLargeIcon);
    }

    private void updateLargeIcon(int iconId) {
        icon.setImageResource(ProfileIconHelper.getIconRes(iconId));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public String getName() {
        return "EditProfile";
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    public static void start(Activity activity, Profile profile) {
        Intent i = new Intent(activity, EditProfileActivity.class);
        i.putExtra(EXTRA_PROFILE, profile);
        activity.startActivity(i);
    }
}
