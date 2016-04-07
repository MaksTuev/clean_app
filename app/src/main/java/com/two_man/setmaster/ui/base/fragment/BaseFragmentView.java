package com.two_man.setmaster.ui.base.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import ru.litres.android.audio.ui.app.App;
import ru.litres.android.audio.ui.app.AppComponent;
import ru.litres.android.audio.ui.base.HasName;
import ru.litres.android.audio.ui.base.HasPresenter;
import ru.litres.android.audio.ui.base.activity.BaseActivity;
import ru.litres.android.audio.util.log.LogServerUtil;

/**
 * базовый класс для вью, основанной на Fragment
 */
public abstract class BaseFragmentView extends Fragment implements HasPresenter, HasName{

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
        getPresenter().onLoad();
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

    public BaseActivity getBaseActivity(){
        return (BaseActivity)getActivity();
    }
}
