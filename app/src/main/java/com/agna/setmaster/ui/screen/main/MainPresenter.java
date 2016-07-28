package com.agna.setmaster.ui.screen.main;

import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.module.profile.ProfileService;
import com.agna.setmaster.module.profile.event.ChangedStatus;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.fragment.PerFragment;
import com.agna.setmaster.ui.navigation.Navigator;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

/**
 *
 */
@PerFragment
public class MainPresenter extends BasePresenter<MainFragmentView> {

    private ProfileService profileService;
    private Navigator navigator;
    private ArrayList<Profile> profiles = new ArrayList<>();

    @Inject
    public MainPresenter(ProfileService profileService, Navigator navigator) {
        this.profileService = profileService;
        this.navigator = navigator;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        showData();
        observeChanges();
    }

    private void observeChanges() {
        profileService.observeProfileChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event.getStatus() == ChangedStatus.DELETED) {
                        showData();
                    } else {
                        showData();
                    }
                });
    }

    private void showData() {
        profiles = profileService.getAllProfiles();
        getView().showProfiles(profiles);
    }

    public void createNewProfile() {
        navigator.openNewProfile();
    }

    public void onStart() {
        showData();
    }

    public void openProfile(Profile profile) {
        navigator.openProfile(profile);
    }
}
