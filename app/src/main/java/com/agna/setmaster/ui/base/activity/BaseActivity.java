package com.agna.setmaster.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.agna.setmaster.app.App;
import com.agna.setmaster.app.AppComponent;


/**
 * бызовый класс всех Activity
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected AppComponent getApplicationComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
