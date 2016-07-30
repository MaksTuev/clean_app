/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.module.condition.simple.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.agna.setmaster.app.App;

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
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        Timber.d("WifiStatusBroadcastReceiver: " + intent);
        String action = intent.getAction();
        Log.d("TEMP", action);
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                if (info.isConnected()) {
                    wifiConditionChecker.onConnected();
                } else {
                    wifiConditionChecker.onDisconnected();
                }
            }
        }
    }

}
