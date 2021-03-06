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
package com.agna.setmaster.ui.screen.condition.wifi;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.condition.WiFiCondition;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;
import com.agna.setmaster.ui.util.ProfileViewUtil;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 *
 */
public class ChangeWifiConditionActivity extends ChangeConditionBaseActivityView {
    public static final String EXTRA_CONDITION = "EXTRA_CONDITION";
    public static final String EXTRA_ACCENT_COLOR = "EXTRA_ACCENT_COLOR";
    @Inject
    ChangeWifiConditionPresenter presenter;


    private TextView name;
    private View headerContainer;
    private int accentColor;
    private View saveBtn;
    private View declineBtn;
    private RecyclerView networkList;
    private WifiNetworkAdapter adapter;

    @Override
    protected void satisfyDependencies() {
        WiFiCondition condition = (WiFiCondition) getIntent().getSerializableExtra(EXTRA_CONDITION);
        DaggerChangeWifiConditionComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(getApplicationComponent())
                .changeWifiConditionModule(new ChangeWifiConditionModule(condition))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, 0);

        findViews();
        initList();
        initListeners();
    }

    private void initListeners() {
        saveBtn.setOnClickListener(v -> save());
        declineBtn.setOnClickListener(v -> goBack());
    }


    private void save() {
        presenter.saveCondition(adapter.getSelectedNetwork());
    }

    @Override
    public void onBackPressed() {
        save();
    }

    private void initList() {
        adapter = new WifiNetworkAdapter(networkList);
        adapter.setAccentColor(accentColor);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
    }

    private void findViews() {
        saveBtn = findViewById(R.id.save_btn);
        declineBtn = findViewById(R.id.decline_btn);
        name = (TextView) findViewById(R.id.condition_name);
        headerContainer = findViewById(R.id.condition_header);
        networkList = (RecyclerView) findViewById(R.id.wifi_network_list);
    }

    public void bind(WiFiCondition condition) {
        adapter.setSelectedNetwork(condition.getNetworkName());
    }

    @Override
    protected int getContentView() {
        return R.layout.change_wifi_condition_activity;
    }

    @Override
    public String getName() {
        return "ChangeWifiConditionActivity";
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    public static void start(Activity activity, WiFiCondition condition, Profile profile) {
        Intent i = new Intent(activity, ChangeWifiConditionActivity.class);
        i.putExtra(EXTRA_CONDITION, condition);
        i.putExtra(EXTRA_ACCENT_COLOR, ProfileViewUtil.getProfileAccentColor(activity, profile));
        activity.startActivityForResult(i, 1);
    }

    public void showNetworks(ArrayList<WifiConfiguration> wifiNetworks) {
        adapter.showNetworks(wifiNetworks);
    }
}
