package com.two_man.setmaster.ui.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.view.View;

import com.two_man.setmaster.ui.app.App;
import com.two_man.setmaster.ui.app.AppComponent;
import com.two_man.setmaster.ui.base.BaseView;
import com.two_man.setmaster.ui.base.HasName;
import com.two_man.setmaster.ui.base.HasPresenter;
import com.two_man.setmaster.ui.base.activity.BaseActivity;
import com.two_man.setmaster.util.log.LogServerUtil;

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
        if(savedInstanceState != null){
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
        return ((App)getActivity().getApplication()).getAppComponent();
    }

    @Override
    public void goBack() {
        getActivity().onBackPressed();
    }

    public BaseActivity getBaseActivity(){
        return (BaseActivity)getActivity();
    }
}
