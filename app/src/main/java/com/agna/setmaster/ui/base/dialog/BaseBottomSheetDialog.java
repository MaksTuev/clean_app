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
package com.agna.setmaster.ui.base.dialog;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.HasName;
import com.agna.setmaster.ui.base.activity.BaseActivityView;
import com.agna.setmaster.ui.base.fragment.BaseFragmentView;
import com.agna.setmaster.app.log.RemoteLogger;

/**
 *
 */
public abstract class BaseBottomSheetDialog extends BottomSheetDialogFragment implements HasName {
    private static final String STATE_PARENT_TYPE = "state_parent";

    private Parent parentType;

    void show(AppCompatActivity parentActivity) {
        parentType = Parent.ACTIVITY;
        show(parentActivity.getSupportFragmentManager());
    }

    void show(Fragment parentFragment) {
        parentType = Parent.FRAGMENT;
        this.setTargetFragment(parentFragment, 0);
        show(parentFragment.getFragmentManager());
    }

    protected void show(FragmentManager fragmentManager) {
        show(fragmentManager, this.getClass().getSimpleName());
    }

    protected final <T> T getListener(Class<T> listenerClass) {
        BasePresenter presenter = null;
        switch (parentType) {
            case ACTIVITY:
                presenter = ((BaseActivityView) getActivity()).getPresenter();
                break;
            case FRAGMENT:
                presenter = ((BaseFragmentView) getTargetFragment()).getPresenter();
                break;
        }
        T listener = (T) presenter;
        return listener;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_PARENT_TYPE, parentType);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RemoteLogger.logViewCreated(this);
        if (savedInstanceState != null) {
            parentType = (Parent) savedInstanceState.getSerializable(STATE_PARENT_TYPE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RemoteLogger.logViewDestroyed(this);
    }

    public enum Parent {
        ACTIVITY,
        FRAGMENT
    }
}
