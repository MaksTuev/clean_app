package com.agna.setmaster.ui.base.dialog;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public class ActivityDialogManager implements DialogManager {
    private AppCompatActivity activity;

    @Inject
    public ActivityDialogManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void show(BaseDialog dialog) {
        dialog.show(activity);
    }

    @Override
    public void show(BaseBottomSheetDialog dialog) {
        dialog.show(activity);
    }
}
