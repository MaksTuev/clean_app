package com.two_man.setmaster.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.two_man.setmaster.app.App;
import com.two_man.setmaster.app.AppComponent;
import com.two_man.setmaster.ui.base.fragment.BaseFragmentView;


/**
 * бызовый класс всех Activity
 * Предоставляет {@link ContainerActivityComponent} для экранов, основанных на {@link BaseFragmentView}
 */
public class BaseActivity extends AppCompatActivity {

    private ContainerActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
    }

    private void initActivityComponent() {
        this.activityComponent = DaggerContainerActivityComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    /**
     * @return компонент, необходимый для экранов, основанных на фрагментах
     */
    public ContainerActivityComponent getContainerActivityComponent() {
        return activityComponent;
    }

    protected AppComponent getApplicationComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
