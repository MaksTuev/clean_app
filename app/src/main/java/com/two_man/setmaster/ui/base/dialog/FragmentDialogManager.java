package com.two_man.setmaster.ui.base.dialog;

import android.app.Fragment;

import javax.inject.Inject;

public class FragmentDialogManager implements DialogManager {

    private Fragment fragment;

    @Inject
    public FragmentDialogManager(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void show(BaseDialog dialog) {
        dialog.show(fragment);
    }
}
