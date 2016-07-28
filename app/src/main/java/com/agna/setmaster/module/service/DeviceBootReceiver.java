package com.agna.setmaster.module.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.agna.setmaster.app.App;

import javax.inject.Inject;

/**
 *
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    @Inject
    AppServiceInteractor appServiceInteractor;

    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        appServiceInteractor.start();
    }
}
