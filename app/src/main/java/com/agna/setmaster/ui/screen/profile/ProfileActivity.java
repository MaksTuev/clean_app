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
package com.agna.setmaster.ui.screen.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.domain.setting.Setting;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.base.activity.BaseActivityView;
import com.agna.setmaster.ui.screen.profile.condition.ConditionSetPagerAdapter;
import com.agna.setmaster.ui.screen.profile.condition.ConditionSetView;
import com.agna.setmaster.ui.screen.profile.setting.SettingGridAdapter;
import com.agna.setmaster.ui.util.ProfileViewUtil;
import com.agna.setmaster.ui.util.StatusBarUtil;
import com.agna.setmaster.util.ProfileIconHelper;

import javax.inject.Inject;

import me.relex.circleindicator.CircleIndicator;

/**
 *
 */
public class ProfileActivity extends BaseActivityView {

    public static final String EXTRA_PROFILE = "EXTRA_PROFILE";
    @Inject
    ProfilePresenter presenter;

    private Toolbar toolbar;
    private TextView name;
    private TextView status;
    private RecyclerView settingsGrid;
    private ViewPager conditionPager;
    private CircleIndicator conditionPagerIndicator;
    private View headerContainer;
    private ImageView profileIcon;
    private View addConditionFab;

    private SettingGridAdapter settingGridAdapter;
    private ConditionSetPagerAdapter conditionSetAdapter;

    @Override
    protected void satisfyDependencies() {
        Profile profile = (Profile) getIntent().getSerializableExtra(EXTRA_PROFILE);
        DaggerProfileComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .profileScreenModule(new ProfileScreenModule(profile))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    public String getName() {
        return "Profile";
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initViews();
        initListeners();
        initToolbar();
        initSettingGrid();
        initConditionPager();
    }

    private void initConditionPager() {
        conditionSetAdapter = new ConditionSetPagerAdapter();
        conditionPager.setAdapter(conditionSetAdapter);
        conditionPagerIndicator.setViewPager(conditionPager);
        conditionSetAdapter.setOnConditionActionListener(onConditionActionListener);
    }

    private void initListeners() {
        addConditionFab.setOnClickListener(v -> {
            presenter.openAddCondition(getSelectedConditionSetId());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(v -> goBack());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.inflateMenu(R.menu.profile_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.profile_edit_menu) {
                presenter.editProfile();
            } else if (item.getItemId() == R.id.profile_delete_menu) {
                presenter.deleteProfile();
            }
            return true;
        });
    }

    private void initViews() {
        profileIcon.setColorFilter(ContextCompat.getColor(this, R.color.profile_icon_half_transparent), PorterDuff.Mode.DST_IN);

    }

    private void initSettingGrid() {
        settingGridAdapter = new SettingGridAdapter(settingsGrid);
        settingGridAdapter.setOnAddSettingClickListener(this::onAddSettingClick);
        settingGridAdapter.setOnSettingClickListener(this::onSettingClick);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        headerContainer = findViewById(R.id.profile_header);
        name = (TextView) findViewById(R.id.profile_name);
        status = (TextView) findViewById(R.id.profile_status);
        settingsGrid = (RecyclerView) findViewById(R.id.profile_settings_grid);
        conditionPager = (ViewPager) findViewById(R.id.profile_conditions_pager);
        conditionPagerIndicator = (CircleIndicator) findViewById(R.id.profile_pager_indicator);
        profileIcon = (ImageView) findViewById(R.id.profile_icon);
        addConditionFab = findViewById(R.id.profile_add_fab);
    }

    public void bindProfile(Profile profile) {
        StatusBarUtil.changeColor(this, profile.isActive());
        name.setText(profile.getName());
        status.setText(ProfileViewUtil.getProfileStatusText(profile));
        settingGridAdapter.showSettings(profile.getSettings(), presenter.allowAddSetting());
        profileIcon.setImageResource(ProfileIconHelper.getIconRes(profile.getIconId()));
        headerContainer.setBackgroundColor(ProfileViewUtil.getProfileAccentColor(this, profile));
        conditionSetAdapter.showConditionSets(profile.getConditionSets());
    }

    private void onAddSettingClick() {
        presenter.chooseSetting();
    }

    private void onSettingClick(Setting setting, int yCenterPosition) {
        presenter.openChangeSetting(setting);
    }

    private ConditionSetView.OnConditionActionListener onConditionActionListener = new ConditionSetView.OnConditionActionListener() {
        @Override
        public void onClick(String conditionSetId, Condition condition) {
            presenter.openChangeCondition(conditionSetId, condition);
        }

        @Override
        public void onDelete(String conditionSetId, Condition condition) {
            presenter.deleteCondition(conditionSetId, condition);
        }
    };


    public static void start(Activity activity, Profile profile) {
        Intent i = new Intent(activity, ProfileActivity.class);
        i.putExtra(EXTRA_PROFILE, profile);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(i);
    }

    public String getSelectedConditionSetId() {
        return conditionSetAdapter.getConditionSetId(conditionPager.getCurrentItem());
    }

    public void openConditionSet(int index) {
        new Handler().postDelayed(() -> {
            try {
                conditionPager.setCurrentItem(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 300);

    }
}
