package com.two_man.setmaster.ui.base.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.HasName;
import com.two_man.setmaster.ui.base.activity.BaseActivityView;
import com.two_man.setmaster.ui.base.fragment.BaseFragmentView;
import com.two_man.setmaster.util.log.LogServerUtil;

/**
 * базовый класс диалога
 * Этот диалог следует показывать из Presenter'a через  {@link DialogManager}
 * Диалог позволяет получиить листенер с помощью метода {@link this#getListener(Class)}
 * В качестве листенера ВСЕГДА выступает тот же Presenter, с которого и был показан диалог,
 * поэтому Presenter должен реализовать интерфейс, который будет передан в метод {@link this#getListener(Class)}
 */
public abstract class BaseDialog extends DialogFragment implements HasName {

    private static final String STATE_PARENT_TYPE = "state_parent";

    private Parent parentType;

    void show(Activity parentActivity) {
        parentType = Parent.ACTIVITY;
        show(parentActivity.getFragmentManager());
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
