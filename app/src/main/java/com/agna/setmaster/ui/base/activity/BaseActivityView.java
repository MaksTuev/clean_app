package com.agna.setmaster.ui.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;

import com.agna.setmaster.ui.base.BaseView;
import com.agna.setmaster.ui.base.HasName;
import com.agna.setmaster.ui.base.HasPresenter;
import com.agna.setmaster.app.log.LogServerUtil;

/**
 * базовый класс для вью, основанной на Activity
 */
public abstract class BaseActivityView extends BaseActivity implements BaseView, HasPresenter, HasName {


    private Handler handler = new Handler();
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
        if (savedInstanceState != null) {
            handler.post(() -> getPresenter().onRestore(savedInstanceState));
        }
        handler.post(() -> getPresenter().onLoad());
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
    public void goBack() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        LogServerUtil.logViewDestroyed(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getPresenter().onActivityResult(requestCode, resultCode, data);
    }
}
