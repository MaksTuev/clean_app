package com.two_man.setmaster.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;

import ru.litres.android.audio.ui.base.HasName;
import ru.litres.android.audio.ui.base.HasPresenter;
import ru.litres.android.audio.util.log.LogServerUtil;

/**
 * базовый класс для вью, основанной на Activity
 */
public abstract class BaseActivityView extends BaseActivity implements HasPresenter, HasName {

    /**
     * в реализации этого метода необходимо удовлетворить зависимости
     */
    protected abstract void satisfyDependencies();

    @LayoutRes
    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogServerUtil.logViewCreated(this);
        setContentView(getContentView());
        satisfyDependencies();
        initPresenter();
        if(savedInstanceState != null){
            getPresenter().onRestore(savedInstanceState);
        }
        getPresenter().onLoad();
    }

    @Override
    @CallSuper
    public void initPresenter() {
        getPresenter().attachView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPresenter().onSave(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        LogServerUtil.logViewDestroyed(this);
    }

}
