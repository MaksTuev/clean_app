package com.two_man.setmaster.ui.screen.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.activity.ActivityModule;
import com.two_man.setmaster.ui.base.activity.BaseActivityView;
import com.two_man.setmaster.ui.screen.editprofile.EditProfileActivity;

import javax.inject.Inject;

/**
 *
 */
public class ProfileActivity extends BaseActivityView {

    public static final String EXTRA_PROFILE = "EXTRA_PROFILE";
    @Inject
    ProfilePresenter presenter;

    @Override
    protected void satisfyDependencies() {
        DaggerProfileComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        Profile profile = (Profile)getIntent().getSerializableExtra(EXTRA_PROFILE);
        presenter.init(profile);
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

    public static void start(Activity activity, Profile profile) {
        Intent i = new Intent(activity, EditProfileActivity.class);
        i.putExtra(EXTRA_PROFILE, profile);
        activity.startActivity(i);
    }
}
