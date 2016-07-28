package com.agna.setmaster.ui.base.dialog;


import android.support.v4.app.Fragment;

import com.agna.setmaster.ui.base.PerScreen;

import javax.inject.Inject;

@PerScreen
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

    @Override
    public void show(BaseBottomSheetDialog dialog) {
        dialog.show(fragment);
    }
}
