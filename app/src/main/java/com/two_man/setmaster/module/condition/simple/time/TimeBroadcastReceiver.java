package com.two_man.setmaster.module.condition.simple.time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.two_man.setmaster.app.App;

import javax.inject.Inject;

import timber.log.Timber;

/**
 *
 */
public class TimeBroadcastReceiver extends BroadcastReceiver {
    @Inject
    TimeConditionChecker timeConditionChecker;


    @Override
    public void onReceive(Context context, Intent intent) {
        ((App)context.getApplicationContext()).getAppComponent().inject(this);
        Timber.v("alarm");
        timeConditionChecker.onAlarmReceived(intent);
    }
}
