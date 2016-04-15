package com.two_man.setmaster.module.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.two_man.setmaster.app.App;
import com.two_man.setmaster.interactor.InitializeAppInteractor;

import javax.inject.Inject;

/**
 *
 */
public class AppService extends Service {

    @Inject
    InitializeAppInteractor initializeAppInteractor;

    @Override
    public void onCreate() {
        super.onCreate();
        ((App)this.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initializeAppInteractor.initialize()
                .subscribe();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
