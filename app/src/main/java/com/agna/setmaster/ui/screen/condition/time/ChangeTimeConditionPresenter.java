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
package com.agna.setmaster.ui.screen.condition.time;

import android.app.Activity;
import android.content.Intent;

import com.agna.setmaster.domain.condition.DayOfWeek;
import com.agna.setmaster.domain.condition.TimeCondition;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.PerScreen;
import com.agna.setmaster.ui.base.dialog.DialogManager;
import com.agna.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

/**
 *
 */
@PerScreen
public class ChangeTimeConditionPresenter extends BasePresenter<ChangeTimeConditionActivity> {

    private DialogManager dialogManager;
    private TimeCondition condition;

    @Inject
    public ChangeTimeConditionPresenter(DialogManager dialogManager, TimeCondition timeCondition) {

        this.dialogManager = dialogManager;
        this.condition = timeCondition;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        getView().bind(condition);
    }

    public void saveCondition(Date from, Date to, ArrayList<DayOfWeek> days) {
        condition.setFrom(from);
        condition.setTo(to);
        condition.setDays(days);
        Intent i = new Intent();
        i.putExtra(ChangeConditionBaseActivityView.EXTRA_CONDITION, condition);
        getView().goBack(Activity.RESULT_OK, i);
    }
}
