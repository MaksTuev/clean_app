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
package com.agna.setmaster.ui.screen.profile.condition.add;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

import com.agna.setmaster.R;
import com.agna.setmaster.ui.base.dialog.BaseBottomSheetDialog;

/**
 *
 */
public class AddConditionOrConditionSetDialog extends BaseBottomSheetDialog {
    private BottomSheetBehavior<View> mBottomSheetBehavior;


    private void initViews(View view) {
        View addConditionSetBtn = view.findViewById(R.id.add_condition_set);
        View addConditionBtn = view.findViewById(R.id.add_condition);

        addConditionSetBtn.setOnClickListener(v -> {
            getListener(AddConditionOrConditionSetDialogListener.class).onNewConditionSet();
            dismiss();
        });

        addConditionBtn.setOnClickListener(v -> {
            getListener(AddConditionOrConditionSetDialogListener.class).onNewCondition();
            dismiss();
        });
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.add_condition_or_condition_set_dialog, null);
        dialog.setContentView(contentView);
        mBottomSheetBehavior = BottomSheetBehavior.from(((View) contentView.getParent()));
        new Handler().postDelayed(() -> mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED), 200);
        initViews(contentView);
    }

    public static AddConditionOrConditionSetDialog newInstance() {
        AddConditionOrConditionSetDialog dialog = new AddConditionOrConditionSetDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public String getName() {
        return "AddConditionOrConditionSetDialog";
    }

    public interface AddConditionOrConditionSetDialogListener {
        void onNewConditionSet();

        void onNewCondition();
    }
}
