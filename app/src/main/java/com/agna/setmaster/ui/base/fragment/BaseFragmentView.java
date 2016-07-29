package com.agna.setmaster.ui.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.view.View;

import com.agna.setmaster.app.App;
import com.agna.setmaster.app.AppComponent;
import com.agna.setmaster.ui.base.BaseView;
import com.agna.setmaster.ui.base.HasName;
import com.agna.setmaster.ui.base.HasPresenter;
import com.agna.setmaster.ui.base.activity.BaseActivity;
import com.agna.setmaster.app.log.LogServerUtil;

/**
 * базовый класс для вью, основанной на Fragment
 */
public abstract class BaseFragmentView extends android.support.v4.app.Fragment implements BaseView, HasPresenter, HasName {

    /**
     * в реализации этого метода необходимо удовлетворить зависимости
     */
    protected abstract void satisfyDependencies();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogServerUtil.logViewCreated(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        satisfyDependencies();
        initPresenter();
        if (savedInstanceState != null) {
            getPresenter().onRestore(savedInstanceState);
        }
        new Handler().post(() -> getPresenter().onLoad());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    @CallSuper
    public void initPresenter() {
        getPresenter().attachView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getPresenter().onSave(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        LogServerUtil.logViewDestroyed(this);
    }

    protected AppComponent getApplicationComponent() {
        return ((App) getActivity().getApplication()).getAppComponent();
    }

    @Override
    public void goBack() {
        getActivity().onBackPressed();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
