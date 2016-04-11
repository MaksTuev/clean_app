package com.two_man.setmaster.ui.screen.main;

import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.profile.event.ChangedStatus;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.fragment.PerFragment;
import com.two_man.setmaster.ui.navigation.Navigator;

import java.util.ArrayList;

import javax.inject.Inject;

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
}
