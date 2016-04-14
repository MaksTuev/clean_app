package com.two_man.setmaster.module.condition.simple.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.two_man.setmaster.ui.app.App;

import javax.inject.Inject;

import timber.log.Timber;

/**
 *
 */
public class WifiStatusBroadcastReceiver extends BroadcastReceiver {
    @Inject
    WifiConditionChecker wifiConditionChecker;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App)context.getApplicationContext()).getAppComponent().inject(this);
        Timber.d("WifiStatusBroadcastReceiver: "+ intent);
        String action = intent.getAction();
        Log.d("TEMP", action);
        if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                if(info.isConnected()){
                    wifiConditionChecker.onConnected();
                } else {
                    wifiConditionChecker.onDisconnected();
                }
            }
        }
    }

}
