package com.agna.setmaster.ui.screen.profile.setting.change;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 *
 */
public class ChangeSettingDialog extends Dialog {
    public ChangeSettingDialog(Context context) {
        super(context);
    }

    public ChangeSettingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ChangeSettingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
