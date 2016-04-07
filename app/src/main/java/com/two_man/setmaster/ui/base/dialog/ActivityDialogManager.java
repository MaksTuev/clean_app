package com.two_man.setmaster.ui.base.dialog;

import android.app.Activity;

import javax.inject.Inject;

public class ActivityDialogManager implements DialogManager {
    private Activity activity;

    @Inject
    public ActivityDialogManager(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show(BaseDialog dialog) {
        dialog.show(activity);
    }
}
