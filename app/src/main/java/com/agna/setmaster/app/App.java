package com.agna.setmaster.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.agna.setmaster.BuildConfig;
import com.agna.setmaster.util.log.CrashlyticsTree;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        initFabric();
        initLogging();
    }

    private void initFabric() {
        Fabric.with(this, new Crashlytics());
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    private void initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }
    }
}
