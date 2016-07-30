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
package com.agna.setmaster.ui.screen.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.fragment.BaseFragmentView;
import com.agna.setmaster.ui.base.fragment.FragmentModule;
import com.agna.setmaster.ui.screen.main.widget.ProfileListItemView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 *
 */
public class MainFragmentView extends BaseFragmentView implements ProfileListItemView.OnProfileActionListener {

    @Inject
    MainPresenter presenter;

    private RecyclerView profilesRecyclerView;
    private ProfilesAdapter profilesAdapter;
    private View addProfileBtn;

    public static Fragment newInstance() {
        Fragment fragment = new MainFragmentView();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilesRecyclerView = (RecyclerView) view.findViewById(R.id.main_profiles_recycler_view);
        addProfileBtn = view.findViewById(R.id.main_add_fab);
        initRecyclerView();
        initListeners();
    }

    private void initListeners() {
        addProfileBtn.setOnClickListener(v -> {
            presenter.createNewProfile();
        });
    }

    public void showProfiles(ArrayList<Profile> profiles) {
        profilesAdapter.showData(profiles);
    }

    public void updateProfile(int position) {
        profilesAdapter.notifyItemChanged(position);
    }

    private void initRecyclerView() {
        profilesAdapter = new ProfilesAdapter(profilesRecyclerView);
        profilesAdapter.setOnProfileActionListener(this);
    }

    @Override
    protected void satisfyDependencies() {
        DaggerMainComponent.builder()
                .appComponent(getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public String getName() {
        return "Main";
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onProfileClick(Profile profile) {
        presenter.openProfile(profile);
    }
}
