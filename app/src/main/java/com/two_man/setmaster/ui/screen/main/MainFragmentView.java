package com.two_man.setmaster.ui.screen.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.fragment.BaseFragmentView;
import com.two_man.setmaster.ui.base.fragment.FragmentModule;
import com.two_man.setmaster.ui.screen.main.widget.ProfileListItemView;

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

    public static Fragment newInstance(){
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
        profilesRecyclerView = (RecyclerView)view.findViewById(R.id.main_profiles_recycler_view);
        addProfileBtn = view.findViewById(R.id.main_add_fab);
        initRecyclerView();
        initListeners();
    }

    private void initListeners() {
        addProfileBtn.setOnClickListener(v -> {presenter.createNewProfile();});
    }

    public void showProfiles(ArrayList<Profile> profiles){
        profilesAdapter.showData(profiles);
    }

    public void updateProfile(int position){
        profilesAdapter.notifyItemChanged(position);
    }

    private void initRecyclerView() {
        profilesAdapter = new ProfilesAdapter(profilesRecyclerView);
        profilesAdapter.setOnProfileActionListener(this);
    }

    @Override
    protected void satisfyDependencies() {
        DaggerMainComponent.builder()
                .containerActivityComponent(getBaseActivity().getContainerActivityComponent())
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
