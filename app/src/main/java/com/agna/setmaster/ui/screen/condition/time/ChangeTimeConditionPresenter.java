package com.agna.setmaster.ui.screen.condition.time;

import android.app.Activity;
import android.content.Intent;

import com.agna.setmaster.entity.condition.DayOfWeek;
import com.agna.setmaster.entity.condition.TimeCondition;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.dialog.DialogManager;
import com.agna.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

/**
 *
 */
public class ChangeTimeConditionPresenter extends BasePresenter<ChangeTimeConditionActivity> {

    private DialogManager dialogManager;
    private TimeCondition condition;

    @Inject
    public ChangeTimeConditionPresenter(DialogManager dialogManager) {

        this.dialogManager = dialogManager;
    }

    public void init(TimeCondition condition) {
        this.condition = condition;
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
