package com.agna.setmaster.ui.screen.condition;

import android.content.Intent;

import com.agna.setmaster.ui.base.activity.BaseActivityView;

/**
 *
 */
public abstract class ChangeConditionBaseActivityView extends BaseActivityView {
    public static final String EXTRA_CONDITION = "EXTRA_CONDITION";

    public void goBack(int result, Intent data) {
        setResult(result, data);
        finish();
    }
}
