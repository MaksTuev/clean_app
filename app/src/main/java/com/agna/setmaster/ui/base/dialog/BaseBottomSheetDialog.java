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
import com.agna.setmaster.util.log.LogServerUtil;

/**
 *
 */
public abstract class BaseBottomSheetDialog extends BottomSheetDialogFragment implements HasName{
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

    protected void show(FragmentManager fragmentManager){
        show(fragmentManager, this.getClass().getSimpleName());
    }

    protected final <T> T getListener(Class<T> listenerClass){
        BasePresenter presenter = null;
        switch (parentType){
            case ACTIVITY:
                presenter = ((BaseActivityView)getActivity()).getPresenter();
                break;
            case FRAGMENT:
                presenter = ((BaseFragmentView)getTargetFragment()).getPresenter();
                break;
        }
        T listener = (T)presenter;
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
        LogServerUtil.logViewCreated(this);
        if(savedInstanceState != null){
            parentType = (Parent)savedInstanceState.getSerializable(STATE_PARENT_TYPE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogServerUtil.logViewDestroyed(this);
    }

    public enum Parent {
        ACTIVITY,
        FRAGMENT
    }
}
