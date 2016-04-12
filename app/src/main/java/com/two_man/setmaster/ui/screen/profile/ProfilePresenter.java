package com.two_man.setmaster.ui.screen.profile;

import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.module.profile.ProfileService;
import com.two_man.setmaster.module.profile.event.ChangedStatus;
import com.two_man.setmaster.module.profile.event.ProfileChangedEvent;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.activity.PerActivity;
import com.two_man.setmaster.ui.base.dialog.DialogManager;
import com.two_man.setmaster.ui.navigation.Navigator;
import com.two_man.setmaster.ui.screen.profile.setting.changesetting.OnSettingChangeListener;
import com.two_man.setmaster.ui.screen.profile.setting.changesetting.SettingChangeDialogCreator;
import com.two_man.setmaster.ui.screen.profile.setting.choosesetting.ChooseSettingDialog;
import com.two_man.setmaster.ui.util.SettingUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 *
 */
@PerActivity
public class ProfilePresenter extends BasePresenter<ProfileActivity> implements ChooseSettingDialog.OnSettingChosenListener,
        OnSettingChangeListener {

    private ProfileService profileService;
    private Navigator navigator;
    private DialogManager dialogManager;
    private ArrayList<Class<? extends Setting>> supportedSettings;
    private SettingChangeDialogCreator settingChangeDialogCreator;
    private Profile profile;

    @Inject
    public ProfilePresenter(ProfileService profileService,
                            Navigator navigator,
                            DialogManager dialogManager,
                            ArrayList<Class<? extends Setting>> supportedSettings,
                            SettingChangeDialogCreator settingChangeDialogCreator) {
        this.profileService = profileService;
        this.navigator = navigator;
        this.dialogManager = dialogManager;
        this.supportedSettings = supportedSettings;
        this.settingChangeDialogCreator = settingChangeDialogCreator;
    }

    public void init(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        initListeners();
        getView().bindProfile(profile);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initListeners() {
        Subscription onProfileChangedSubscription = profileService.observeProfileChanged()
                .flatMap(event -> event.getProfile().getId().equals(profile.getId())
                        ? Observable.just(event)
                        : Observable.empty())
                .subscribe(this::onProfileChanged);
        addToSubscriptions(onProfileChangedSubscription);
    }

    private void onProfileChanged(ProfileChangedEvent event){
        if(event.getStatus() == ChangedStatus.DELETED){
            getView().goBack();
        } else {
            this.profile = event.getProfile();
            getView().bindProfile(profile);
        }
    }

    public void editProfile() {
        navigator.openEditProfile(profile);
    }

    public void onStart() {
        getView().bindProfile(profile);
    }

    public void chooseSetting() {
        ArrayList<Class<? extends Setting>> settingClasses = new ArrayList<>();
        for(Class<? extends Setting> settingClass : supportedSettings){
            boolean found = false;
            for(Setting setting : profile.getSettings()){
                if(setting.getClass() == settingClass){
                    found = true;
                }
            }
            if(!found){
                settingClasses.add(settingClass);
            }
        }
        dialogManager.show(ChooseSettingDialog.newInstance(settingClasses));
    }

    @Override
    public void onSettingChosen(Class<? extends Setting> settingClass) {
        Setting setting = SettingUtil.newInstance(settingClass);
        profile.addSetting(setting);
        profileService.updateProfile(profile);
        openChangeSetting(setting);
    }

    public void openChangeSetting(Setting setting) {
        dialogManager.show(settingChangeDialogCreator.createDialog(profile, setting));
    }

    @Override
    public void onSettingChanged(Setting setting) {
        profile.updateSetting(setting);
        profileService.updateProfile(profile);
    }

    @Override
    public void onSettingDeleted(Setting setting) {
        profile.deleteSetting(setting);
        profileService.updateProfile(profile);
    }
}
