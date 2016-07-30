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

import android.content.Intent;

import com.agna.setmaster.entity.ConditionSet;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.module.profile.ProfileService;
import com.agna.setmaster.module.profile.event.ChangedStatus;
import com.agna.setmaster.module.profile.event.ProfileChangedEvent;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.dialog.DialogManager;
import com.agna.setmaster.ui.common.navigation.Navigator;
import com.agna.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;
import com.agna.setmaster.ui.screen.profile.condition.add.AddConditionDialog;
import com.agna.setmaster.ui.screen.profile.condition.add.AddConditionOrConditionSetDialog;
import com.agna.setmaster.ui.screen.profile.setting.change.OnSettingChangeListener;
import com.agna.setmaster.ui.screen.profile.setting.change.SettingChangeDialogCreator;
import com.agna.setmaster.ui.screen.profile.setting.choose.ChooseSettingDialog;
import com.agna.setmaster.ui.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 *
 */
@PerScreen
public class ProfilePresenter extends BasePresenter<ProfileActivity> implements
        ChooseSettingDialog.OnSettingChosenListener,
        OnSettingChangeListener,
        AddConditionOrConditionSetDialog.AddConditionOrConditionSetDialogListener,
        AddConditionDialog.AddConditionDialogListener {

    private ProfileService profileService;
    private Navigator navigator;
    private DialogManager dialogManager;
    private ArrayList<Class<? extends Setting>> supportedSettings;
    private SettingChangeDialogCreator settingChangeDialogCreator;
    private ArrayList<Class<? extends Condition>> supportedConditions;

    private Profile profile;

    @Inject
    public ProfilePresenter(Profile profile,
                            ProfileService profileService,
                            Navigator navigator,
                            DialogManager dialogManager,
                            ArrayList<Class<? extends Setting>> supportedSettings,
                            SettingChangeDialogCreator settingChangeDialogCreator,
                            ArrayList<Class<? extends Condition>> supportedConditions) {
        this.profileService = profileService;
        this.navigator = navigator;
        this.dialogManager = dialogManager;
        this.supportedSettings = supportedSettings;
        this.settingChangeDialogCreator = settingChangeDialogCreator;
        this.supportedConditions = supportedConditions;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Condition condition = (Condition) data.getSerializableExtra(ChangeConditionBaseActivityView.EXTRA_CONDITION);
            profile.updateCondition(condition);
            profileService.updateProfile(profile);
        }
    }

    private void initListeners() {
        Subscription onProfileChangedSubscription = profileService.observeProfileChanged()
                .filter(event -> event.getProfile().getId().equals(profile.getId()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onProfileChanged);
        addToSubscriptions(onProfileChangedSubscription);
    }

    private void onProfileChanged(ProfileChangedEvent event) {
        if (event.getStatus() == ChangedStatus.DELETED) {
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
        ArrayList<Class<? extends Setting>> settingClasses = getAllowedSettings();
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

    public void openAddCondition(String conditionSetId) {
        boolean conditionSetEmpty = profile.getConditionSet(conditionSetId).isEmpty();
        ArrayList<Class<? extends Condition>> allowedConditions = getAllowedConditions();
        if (allowedConditions.size() == 0) {
            onNewConditionSet();
            return;
        }
        if (!conditionSetEmpty) {
            dialogManager.show(AddConditionOrConditionSetDialog.newInstance());
        } else {
            dialogManager.show(AddConditionDialog.newInstance(allowedConditions));
        }
    }

    @Override
    public void onNewConditionSet() {
        for (int i = 0; i < profile.getConditionSets().size(); i++) {
            ConditionSet conditionSet = profile.getConditionSets().get(i);
            if (conditionSet.isEmpty()) {
                getView().openConditionSet(i);
                return;
            }
        }
        profile.addConditionSet(new ConditionSet());
        profileService.updateProfile(profile);
        getView().openConditionSet(profile.getConditionSets().size() - 1);
    }

    @Override
    public void onNewCondition() {
        dialogManager.show(AddConditionDialog.newInstance(getAllowedConditions()));
    }

    @Override
    public void onAddCondition(Class<? extends Condition> conditionType) {
        try {
            Condition condition = conditionType.newInstance();
            ConditionSet conditionSet = profile.getConditionSet(getView().getSelectedConditionSetId());
            conditionSet.addCondition(condition);
            profileService.updateProfile(profile);
            navigator.openChangeCondition(condition, profile);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteCondition(String conditionSetId, Condition condition) {
        ConditionSet activeConditionSet = profile.getConditionSet(conditionSetId);
        activeConditionSet.delete(condition);
        List<ConditionSet> emptyConditionSets = new ArrayList<>();
        for (ConditionSet conditionSet : profile.getConditionSets()) {
            if (conditionSet.isEmpty()) {
                emptyConditionSets.add(conditionSet);
            }
        }
        if (emptyConditionSets.size() > 1) {
            profile.deleteConditionSet(emptyConditionSets.get(emptyConditionSets.size() - 1));
        }
        profileService.updateProfile(profile);
    }

    public void openChangeCondition(String conditionSetId, Condition condition) {
        navigator.openChangeCondition(condition, profile);
    }

    public boolean allowAddSetting() {
        return getAllowedSettings().size() != 0;
    }

    public ArrayList<Class<? extends Condition>> getAllowedConditions() {
        ArrayList<Class<? extends Condition>> conditionClasses = new ArrayList<>();
        ConditionSet selectedConditionSet = profile.getConditionSet(getView().getSelectedConditionSetId());
        for (Class<? extends Condition> conditionClass : supportedConditions) {
            boolean found = false;
            for (Condition condition : selectedConditionSet.getConditions()) {
                if (condition.getClass() == conditionClass) {
                    found = true;
                }
            }
            if (!found) {
                conditionClasses.add(conditionClass);
            }
        }
        ;
        return conditionClasses;
    }

    private ArrayList<Class<? extends Setting>> getAllowedSettings() {
        ArrayList<Class<? extends Setting>> settingClasses = new ArrayList<>();
        for (Class<? extends Setting> settingClass : supportedSettings) {
            boolean found = false;
            for (Setting setting : profile.getSettings()) {
                if (setting.getClass() == settingClass) {
                    found = true;
                }
            }
            if (!found) {
                settingClasses.add(settingClass);
            }
        }
        return settingClasses;
    }

    public void deleteProfile() {
        profileService.deleteProfile(profile.getId());
    }
}
