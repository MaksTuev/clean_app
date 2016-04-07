package com.two_man.setmaster.ui.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import ru.litres.android.audio.BuildConfig;
import ru.litres.android.audio.util.log.CrashlyticsTree;
import timber.log.Timber;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initAppComponent();
        initLogging();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
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
